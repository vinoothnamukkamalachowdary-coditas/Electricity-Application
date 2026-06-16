package com.example.demo_Electricity_App.Mapper.Master;

import com.example.demo_Electricity_App.DTO.Master.Response.InvitationResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Invitation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class InvitationMapper {
    public InvitationResponseDTO toResponse(Invitation invitation) {

        InvitationResponseDTO dto = new InvitationResponseDTO();
        dto.setId(invitation.getId());
        dto.setInvitationToken(invitation.getInvitationToken());
        dto.setIssuedTo(invitation.getIssuedTo());
        dto.setRole(invitation.getRole());
        dto.setExpiresAt(LocalDateTime.now());
        dto.setIssuedAt(LocalDateTime.now());
        if(invitation.getIssuedBy() != null){
            dto.setIssuedByEmail(invitation.getIssuedBy().getEmail());
        }
        return dto;
    }
}
