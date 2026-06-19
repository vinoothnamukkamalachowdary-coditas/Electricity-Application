package com.example.demo_Electricity_App.Service.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.MeterTypesRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.MeterTypesResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Tenant;
import com.example.demo_Electricity_App.Entity.Tenant.MeterTypes;
import com.example.demo_Electricity_App.Entity.Tenant.Tenants;
import com.example.demo_Electricity_App.Exception.InvalidOperationException;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Tenant.MeterTypesMapper;
import com.example.demo_Electricity_App.Repository.Master.TenantRepository;
import com.example.demo_Electricity_App.Repository.Tenant.MeterTypesRepository;
import com.example.demo_Electricity_App.Repository.Tenant.TenantsRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MeterTypesService {

    private final MeterTypesRepository meterTypesRepository;
    private final MeterTypesMapper meterTypesMapper;
    private final TenantRepository  tenantRepository;
    private final TenantsRepository tenantsRepository;

    public MeterTypesResponseDTO createMeterType(Long tenantId, @Valid MeterTypesRequestDTO dto) {
        Tenant masterTenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + tenantId));

        if (!masterTenant.isActive()) {
            throw new InvalidOperationException(
                    "Tenant " + tenantId + " is not active and cannot configure meter types");
        }

        if (meterTypesRepository.existsByMeterTypeIgnoreCase(dto.getMeterType())) {
            throw new InvalidOperationException(
                    "Meter type already configured: " + dto.getMeterType());
        }

        Tenants self = tenantsRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active tenant schema has no Tenants record — was onboarding completed?"));

        MeterTypes meterType = new MeterTypes();
        meterType.setMeterType(dto.getMeterType());
        meterType.setRatePerUnit(dto.getRatePerUnit());
        meterType.setPhotoCount(dto.getPhotoCount());
        meterType.setPhoto_interval_seconds(dto.getPhotoIntervalSeconds());
        meterType.setTenant(self);

        return meterTypesMapper.toResponse(meterTypesRepository.save(meterType));
    }

    public MeterTypesResponseDTO getMeterType(Long id) {
        return meterTypesMapper.toResponse(meterTypesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meter type not found: " + id)));
    }

    public List<MeterTypesResponseDTO> getAllMeterTypes() {
        return meterTypesRepository.findAll().stream()
                .map(meterTypesMapper::toResponse).collect(Collectors.toList());
    }

    public MeterTypesResponseDTO updateMeterType(Long id, @Valid MeterTypesRequestDTO dto) {
        MeterTypes meterType = meterTypesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meter type not found: " + id));

        meterType.setMeterType(dto.getMeterType());
        meterType.setRatePerUnit(dto.getRatePerUnit());
        meterType.setPhotoCount(dto.getPhotoCount());
        meterType.setPhoto_interval_seconds(dto.getPhotoIntervalSeconds());

        return meterTypesMapper.toResponse(meterTypesRepository.save(meterType));
    }
}
