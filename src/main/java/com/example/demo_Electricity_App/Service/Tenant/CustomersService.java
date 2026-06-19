package com.example.demo_Electricity_App.Service.Tenant;

import com.example.demo_Electricity_App.DTO.Master.Response.ServiceAreaResponseDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.CustomerEnrollmentRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.CustomerResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.City;
import com.example.demo_Electricity_App.Entity.Master.District;
import com.example.demo_Electricity_App.Entity.Master.State;
import com.example.demo_Electricity_App.Entity.Tenant.Customers;
import com.example.demo_Electricity_App.Entity.Tenant.MeterTypes;
import com.example.demo_Electricity_App.Entity.Tenant.Tenants;
import com.example.demo_Electricity_App.Exception.InvalidOperationException;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Tenant.CustomersMapper;
import com.example.demo_Electricity_App.Repository.Master.CityRepository;
import com.example.demo_Electricity_App.Repository.Master.DistrictRepository;
import com.example.demo_Electricity_App.Repository.Master.StateRepository;
import com.example.demo_Electricity_App.Repository.Tenant.CustomersRepository;
import com.example.demo_Electricity_App.Repository.Tenant.MeterTypesRepository;
import com.example.demo_Electricity_App.Repository.Tenant.TenantsRepository;
import com.example.demo_Electricity_App.Service.EmailService;
import com.example.demo_Electricity_App.Service.Master.MasterUserLookupService;
import com.example.demo_Electricity_App.Tenant.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomersService {
    private static final String MASTER_SCHEMA = "public";

    private final CustomersRepository customersRepository;
    private final MeterTypesRepository meterTypesRepository;
    private final TenantsRepository tenantsRepository;
    private final StateRepository stateRepository;
    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;
    private final MasterUserLookupService masterLookupService;
    private final EmailService emailService;
    private final CustomersMapper mapper;

    public CustomerResponseDTO enroll(@Valid CustomerEnrollmentRequestDTO dto) {
        if (customersRepository.existsByEmail(dto.getEmail())) {
            throw new InvalidOperationException(
                    "A customer is already enrolled with this email: " + dto.getEmail());
        }

        Tenants tenant = tenantsRepository.findById(dto.getTenantId())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + dto.getTenantId()));

        MeterTypes meterType = meterTypesRepository.findById(dto.getMeterTypeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Meter type not found: " + dto.getMeterTypeId()));

        validateGeographyChain(dto.getMasterStateId(), dto.getMasterDistrictId(), dto.getMasterCityId());

        // Confirms the chosen service area really exists and belongs to a real Coditas technician+biller pairing before we let the customer enroll under it.
        ServiceAreaResponseDTO serviceArea = masterLookupService.resolveServiceArea(dto.getMasterServiceAreaId());
        if (serviceArea.getCityId() == null || !serviceArea.getCityId().equals(dto.getMasterCityId())) {
            throw new InvalidOperationException(
                    "Service area " + dto.getMasterServiceAreaId()
                            + " does not belong to city " + dto.getMasterCityId());
        }

        Customers customer = new Customers();
        customer.setCustomerName(dto.getCustomerName());
        customer.setEmail(dto.getEmail());
        customer.setMobile(dto.getMobile());
        customer.setAddress(dto.getAddress());
        customer.setPincode(dto.getPincode());
        customer.setMasterStateId(dto.getMasterStateId());
        customer.setMasterDistrictId(dto.getMasterDistrictId());
        customer.setMasterCityId(dto.getMasterCityId());
        customer.setMasterServiceAreaId(dto.getMasterServiceAreaId());
        customer.setTenants(tenant);
        customer.setMeterType(meterType);

        Customers saved = customersRepository.save(customer);

        emailService.sendEnrollmentConfirmation(saved.getEmail(), saved.getCustomerName(), tenant.getName());

        return mapper.toResponse(saved);
    }

    public CustomerResponseDTO getCustomer(Long id) {
        return mapper.toResponse(customersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id)));
    }

    public List<CustomerResponseDTO> getAllCustomers() {
        return customersRepository.findAll().stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<CustomerResponseDTO> getCustomersByServiceArea(Long masterServiceAreaId) {
        return customersRepository.findByMasterServiceAreaId(masterServiceAreaId).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    private void validateGeographyChain(Long masterStateId, Long masterDistrictId, Long masterCityId) {
        String callerTenant = TenantContext.getTenant();
        try {
            TenantContext.setTenant(MASTER_SCHEMA);

            State state = stateRepository.findById(masterStateId)
                    .orElseThrow(() -> new ResourceNotFoundException("State not found: " + masterStateId));

            District district = districtRepository.findById(masterDistrictId)
                    .orElseThrow(() -> new ResourceNotFoundException("District not found: " + masterDistrictId));

            if (district.getState() == null || !district.getState().getId().equals(state.getId())) {
                throw new InvalidOperationException(
                        "District " + masterDistrictId + " does not belong to state " + masterStateId);
            }

            City city = cityRepository.findById(masterCityId)
                    .orElseThrow(() -> new ResourceNotFoundException("City not found: " + masterCityId));

            if (city.getDistrict() == null || !city.getDistrict().getId().equals(district.getId())) {
                throw new InvalidOperationException(
                        "City " + masterCityId + " does not belong to district " + masterDistrictId);
            }
        } finally {
            if (callerTenant != null) {
                TenantContext.setTenant(callerTenant);
            } else {
                TenantContext.clearTenant();
            }
        }
    }
}
