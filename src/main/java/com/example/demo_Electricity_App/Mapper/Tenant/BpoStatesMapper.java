package com.example.demo_Electricity_App.Mapper.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Response.BpoStatesResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.BpoStates;
import org.springframework.stereotype.Component;

@Component
public class BpoStatesMapper {
    public BpoStatesResponseDTO toResponse(BpoStates bpoState) {
        BpoStatesResponseDTO dto = new BpoStatesResponseDTO();
        dto.setId(bpoState.getId());
        dto.setStateName(bpoState.getStateName());
        dto.setActive(bpoState.isActive());
        dto.setMasterStateId(bpoState.getMasterStateId());
        if (bpoState.getTenant() != null) {
            dto.setTenantId(bpoState.getTenant().getId());
        }
        return dto;
    }
}
