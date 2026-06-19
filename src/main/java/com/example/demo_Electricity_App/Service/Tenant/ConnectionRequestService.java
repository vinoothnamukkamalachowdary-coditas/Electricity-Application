package com.example.demo_Electricity_App.Service.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.ApproveConnectionRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.ConnectionRequestEstablishmentDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.ConnectionRequestResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.ConnectionRequest;
import com.example.demo_Electricity_App.Entity.Tenant.Customers;
import com.example.demo_Electricity_App.Entity.Tenant.MeterTypes;
import com.example.demo_Electricity_App.Entity.Tenant.TenantUsers;
import com.example.demo_Electricity_App.Enums.ConnectionStatus;
import com.example.demo_Electricity_App.Exception.InvalidOperationException;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Tenant.ConnectionRequestMapper;
import com.example.demo_Electricity_App.Repository.Tenant.ConnectionRequestRepository;
import com.example.demo_Electricity_App.Repository.Tenant.CustomersRepository;
import com.example.demo_Electricity_App.Repository.Tenant.MeterTypesRepository;
import com.example.demo_Electricity_App.Repository.Tenant.TenantUsersRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ConnectionRequestService {

    private final ConnectionRequestRepository connectionRequestRepository;
    private final CustomersRepository customersRepository;
    private final MeterTypesRepository meterTypesRepository;
    private final TenantUsersRepository tenantUsersRepository;
    private final ConnectionRequestMapper mapper;

    public ConnectionRequestResponseDTO create(@Valid ConnectionRequestEstablishmentDTO dto) {
        Customers customer = customersRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + dto.getCustomerId()));

        MeterTypes meterType = meterTypesRepository.findById(dto.getMeterTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Meter type not found: " + dto.getMeterTypeId()));

        boolean alreadyPending = connectionRequestRepository
                .findFirstByCustomerIdAndStatus(customer.getId(), ConnectionStatus.REQUESTED)
                .isPresent();
        if (alreadyPending) {
            throw new InvalidOperationException(
                    "Customer " + customer.getId() + " already has a pending connection request");
        }

        ConnectionRequest request = new ConnectionRequest();
        request.setCustomer(customer);
        request.setMeterType(meterType);
        request.setStatus(ConnectionStatus.REQUESTED);
        request.setRequestedAt(LocalDateTime.now());
        request.setMasterServiceAreaId(customer.getMasterServiceAreaId());

        return mapper.toResponse(connectionRequestRepository.save(request));
    }

    public ConnectionRequestResponseDTO approve(Long id, Long approvedByTenantUserId,
                                                @Valid ApproveConnectionRequestDTO dto) {
        ConnectionRequest request = connectionRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Connection request not found: " + id));

        if (request.getStatus() != ConnectionStatus.REQUESTED
                && request.getStatus() != ConnectionStatus.PENDING) {
            throw new InvalidOperationException(
                    "Connection request " + id + " is " + request.getStatus() + " and cannot be approved");
        }

        TenantUsers approver = tenantUsersRepository.findById(approvedByTenantUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Approver tenant user not found: " + approvedByTenantUserId));

        request.setStatus(ConnectionStatus.APPROVED);
        request.setApprovedAt(LocalDateTime.now());
        request.setApprovedBy(approver);

        return mapper.toResponse(connectionRequestRepository.save(request));
    }

    public ConnectionRequestResponseDTO reject(Long id, Long approvedByTenantUserId,
                                               @Valid ApproveConnectionRequestDTO dto) {
        ConnectionRequest request = connectionRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Connection request not found: " + id));

        if (request.getStatus() == ConnectionStatus.INSTALLED) {
            throw new InvalidOperationException(
                    "Connection request " + id + " is already INSTALLED and cannot be rejected");
        }

        TenantUsers approver = tenantUsersRepository.findById(approvedByTenantUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Approver tenant user not found: " + approvedByTenantUserId));

        request.setStatus(ConnectionStatus.REJECTED);
        request.setApprovedAt(LocalDateTime.now());
        request.setApprovedBy(approver);

        return mapper.toResponse(connectionRequestRepository.save(request));
    }

    public void markInstalled(Long id) {
        ConnectionRequest request = connectionRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Connection request not found: " + id));
        request.setStatus(ConnectionStatus.INSTALLED);
        connectionRequestRepository.save(request);
    }

    public ConnectionRequestResponseDTO getById(Long id) {
        return mapper.toResponse(connectionRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Connection request not found: " + id)));
    }

    public List<ConnectionRequestResponseDTO> getByCustomer(Long customerId) {
        return connectionRequestRepository.findByCustomerId(customerId).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<ConnectionRequestResponseDTO> getByStatus(ConnectionStatus status) {
        return connectionRequestRepository.findByStatus(status).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
