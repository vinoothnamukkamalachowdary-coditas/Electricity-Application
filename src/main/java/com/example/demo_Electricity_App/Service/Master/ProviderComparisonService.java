package com.example.demo_Electricity_App.Service.Master;

import com.example.demo_Electricity_App.DTO.Master.Response.ProviderComparisonResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Tenant;
import com.example.demo_Electricity_App.Entity.Tenant.MeterTypes;
import com.example.demo_Electricity_App.Repository.Master.TenantRepository;
import com.example.demo_Electricity_App.Repository.Tenant.MeterTypesRepository;
import com.example.demo_Electricity_App.Tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProviderComparisonService {

    private final TenantRepository tenantRepository;
    private final MeterTypesRepository meterTypesRepository;

    public List<ProviderComparisonResponseDTO> compareByMeterType(String meterType) {
        List<ProviderComparisonResponseDTO> results = new ArrayList<>();

        List<Tenant> activeTenants = tenantRepository.findAll().stream()
                .filter(Tenant::isActive)
                .toList();

        String callerTenant = TenantContext.getTenant();
        try {
            for (Tenant tenant : activeTenants) {
                TenantContext.setTenant(tenant.getSchemaName());

                meterTypesRepository.findByMeterTypeIgnoreCase(meterType)
                        .ifPresent(mt -> results.add(toComparisonRow(tenant, mt)));
            }
        } finally {
            if (callerTenant != null) {
                TenantContext.setTenant(callerTenant);
            } else {
                TenantContext.clearTenant();
            }
        }

        return results;
    }

    private ProviderComparisonResponseDTO toComparisonRow(Tenant tenant, MeterTypes meterType) {
        ProviderComparisonResponseDTO dto = new ProviderComparisonResponseDTO();
        dto.setTenantId(tenant.getId());
        dto.setTenantName(tenant.getName());
        dto.setMeterTypeId(meterType.getId());
        dto.setMeterType(meterType.getMeterType());
        dto.setRatePerUnit(meterType.getRatePerUnit());
        dto.setPhotoCount(meterType.getPhotoCount());
        dto.setPhotoIntervalSeconds(meterType.getPhoto_interval_seconds());
        return dto;
    }
}
