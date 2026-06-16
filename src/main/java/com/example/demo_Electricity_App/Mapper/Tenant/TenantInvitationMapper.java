package com.example.demo_Electricity_App.Mapper.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Response.TenantsInvitationResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.TenantInvitation;
import org.springframework.stereotype.Component;

@Component
public class TenantInvitationMapper {
    public TenantsInvitationResponseDTO toResponse(TenantInvitation invitation){
        TenantsInvitationResponseDTO dto = new TenantsInvitationResponseDTO();
        dto.setId(invitation.getId());
        dto.setInvtationToken(invitation.getInvitationToken());
        dto.setIssuedTo(invitation.getIssuedTo());
        dto.setRole(invitation.getRole());
        dto.setIssuedAt(invitation.getIssuedAt());
        dto.setExpiresAt(invitation.getExpiresAt());
        if (invitation.getIssuedBy() != null) {
            dto.setIssuedByEmail(invitation.getIssuedBy().getEmail());
        }
        return dto;
    }
}