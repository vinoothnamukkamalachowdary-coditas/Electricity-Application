package com.example.demo_Electricity_App.Mapper.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Response.ComplaintResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.Complaints;
import org.springframework.stereotype.Component;

@Component
public class ComplaintsMapper {
    public ComplaintResponseDTO toResponse(Complaints complaint) {
        ComplaintResponseDTO dto = new ComplaintResponseDTO();
        dto.setId(complaint.getId());
        if (complaint.getBpoState() != null) {
            dto.setCustomerId(complaint.getBpoState().getId());
            dto.setCustomerName(complaint.getBpoState().getCustomerName());
        }
        dto.setTitle(complaint.getTitle());
        dto.setComplaintDescription(complaint.getComplaintDescription());
        dto.setStatus(complaint.getStatus());
        if (complaint.getAssignedPersonnel() != null) {
            dto.setAssignedPersonnelId(complaint.getAssignedPersonnel().getId());
            dto.setAssignedPersonnelName(complaint.getAssignedPersonnel().getName());
        }
        dto.setMasterTechnicianId(complaint.getMasterTechnicianId());
        dto.setEscalationLevel(complaint.getEscalationLevel());
        dto.setRaisedAt(complaint.getRaisedAt());
        dto.setResolvedAt(complaint.getResolvedAt());
        dto.setLastUpdatedAt(complaint.getLastUpdatedAt());
        return dto;
    }
}
