package com.example.demo_Electricity_App.Mapper.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Response.MeterInstallationResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.MeterInstallation;
import org.springframework.stereotype.Component;

@Component
public class MeterInstallationMapper {
    public MeterInstallationResponseDTO toResponse(MeterInstallation installation) {
        MeterInstallationResponseDTO dto = new MeterInstallationResponseDTO();
        dto.setId(installation.getId());
        if (installation.getCustomers() != null) {
            dto.setCustomerId(installation.getCustomers().getId());
            dto.setCustomerName(installation.getCustomers().getCustomerName());
        }
        dto.setMeterNumber(installation.getMeterNumber());
        dto.setMasterTechnicianId(installation.getMasterTechnicianId());
        if (installation.getConnectionRequest() != null) {
            dto.setConnectionRequestId(installation.getConnectionRequest().getId());
        }
        dto.setInstallationDate(installation.getInstallationDate());
        return dto;
    }
}
