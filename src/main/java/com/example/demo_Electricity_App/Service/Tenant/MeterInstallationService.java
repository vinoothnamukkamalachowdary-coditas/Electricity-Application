package com.example.demo_Electricity_App.Service.Tenant;

import com.example.demo_Electricity_App.DTO.Master.Response.MasterUserLookupResponseDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.MeterInstallationRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.MeterInstallationResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.ConnectionRequest;
import com.example.demo_Electricity_App.Entity.Tenant.MeterInstallation;
import com.example.demo_Electricity_App.Enums.ConnectionStatus;
import com.example.demo_Electricity_App.Enums.Role;
import com.example.demo_Electricity_App.Exception.InvalidOperationException;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Tenant.MeterInstallationMapper;
import com.example.demo_Electricity_App.Repository.Tenant.ConnectionRequestRepository;
import com.example.demo_Electricity_App.Repository.Tenant.MeterInstallationRepository;
import com.example.demo_Electricity_App.Service.Master.MasterUserLookupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MeterInstallationService {
    private final MeterInstallationRepository meterInstallationRepository;
    private final ConnectionRequestRepository connectionRequestRepository;
    private final MasterUserLookupService masterLookupService;
    private final ConnectionRequestService connectionRequestService;
    private final MeterInstallationMapper mapper;

    //Records a completed physical install.
    public MeterInstallationResponseDTO install(@Valid MeterInstallationRequestDTO dto) {
        ConnectionRequest connectionRequest = connectionRequestRepository.findById(dto.getConnectionRequestId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Connection request not found: " + dto.getConnectionRequestId()));

        if (connectionRequest.getStatus() != ConnectionStatus.APPROVED) {
            throw new InvalidOperationException(
                    "Connection request " + dto.getConnectionRequestId()
                            + " is " + connectionRequest.getStatus() + ", must be APPROVED before installation");
        }

        if (meterInstallationRepository.existsByMeterNumber(dto.getMeterNumber())) {
            throw new InvalidOperationException(
                    "Meter number already in use: " + dto.getMeterNumber());
        }

        // Inter-schema validation: confirm master ID is really a TECHNICIAN …
        MasterUserLookupResponseDTO technician =
                masterLookupService.resolveUserWithRole(dto.getMasterTechnicianId(), Role.TECHNICIAN);

        Long masterServiceAreaId = connectionRequest.getMasterServiceAreaId();
        if (masterServiceAreaId != null) {
            masterLookupService.assertTechnicianBelongsToServiceArea(masterServiceAreaId, technician.getId());
        }

        MeterInstallation installation = new MeterInstallation();
        installation.setCustomers(connectionRequest.getCustomer());
        installation.setMeterNumber(dto.getMeterNumber());
        installation.setMasterTechnicianId(dto.getMasterTechnicianId());
        installation.setConnectionRequest(connectionRequest);
        installation.setInstallationDate(LocalDate.now());

        MeterInstallation saved = meterInstallationRepository.save(installation);

        connectionRequestService.markInstalled(connectionRequest.getId());

        return mapper.toResponse(saved);
    }

    public MeterInstallationResponseDTO getById(Long id) {
        return mapper.toResponse(meterInstallationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meter installation not found: " + id)));
    }

    public List<MeterInstallationResponseDTO> getByCustomer(Long customerId) {
        return meterInstallationRepository.findByCustomersId(customerId).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<MeterInstallationResponseDTO> getByTechnician(Long masterTechnicianId) {
        return meterInstallationRepository.findByMasterTechnicianId(masterTechnicianId).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
