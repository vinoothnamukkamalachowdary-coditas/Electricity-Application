package com.example.demo_Electricity_App.DTO.Tenant.Response;

import com.example.demo_Electricity_App.Enums.ComplaintsStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintResponseDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private String title;
    private String complaintDescription;
    private ComplaintsStatus status;
    private Long assignedPersonnelId;
    private String assignedPersonnelName;
    private Long masterTechnicianId;
    private String masterTechnicianName;
    private Integer escalationLevel;
    private LocalDateTime raisedAt;
    private LocalDateTime resolvedAt;
    private LocalDateTime lastUpdatedAt;
}
