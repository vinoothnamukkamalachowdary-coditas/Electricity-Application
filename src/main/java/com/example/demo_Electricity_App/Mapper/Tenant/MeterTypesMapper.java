package com.example.demo_Electricity_App.Mapper.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Response.MeterTypesResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.MeterTypes;
import org.springframework.stereotype.Component;

@Component
public class MeterTypesMapper {
    public MeterTypesResponseDTO toResponse(MeterTypes meterType) {
        MeterTypesResponseDTO dto = new MeterTypesResponseDTO();
        dto.setId(meterType.getId());
        dto.setMeterType(meterType.getMeterType());
        dto.setRatePerUnit(meterType.getRatePerUnit());
        dto.setPhotoCount(meterType.getPhotoCount());
        dto.setPhotoIntervalSeconds(meterType.getPhoto_interval_seconds());
        if (meterType.getTenant() != null) {
            dto.setTenantId(meterType.getTenant().getId());
            dto.setTenantName(meterType.getTenant().getName());
        }
        return dto;
    }
}
