package com.example.demo_Electricity_App.Mapper.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Response.BpoAssignmentResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.Bpo;
import org.springframework.stereotype.Component;

@Component
public class BpoMapper {
    public BpoAssignmentResponseDTO toResponse(Bpo bpo) {
        BpoAssignmentResponseDTO dto = new BpoAssignmentResponseDTO();
        dto.setId(bpo.getId());
        if (bpo.getTenantUser() != null) {
            dto.setTenantUserId(bpo.getTenantUser().getId());
            dto.setTenantUserName(bpo.getTenantUser().getName());
            dto.setTenantUserRole(bpo.getTenantUser().getRole());
        }
        if (bpo.getBpoState() != null) {
            dto.setBpoStateId(bpo.getBpoState().getId());
            dto.setBpoStateName(bpo.getBpoState().getStateName());
        }
        dto.setMasterDistrictId(bpo.getMasterDistrictId());
        dto.setMasterCityId(bpo.getMasterCityId());
        return dto;
    }
}
