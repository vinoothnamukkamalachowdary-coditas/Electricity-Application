package com.example.demo_Electricity_App.DTO.Master.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PortfolioResponseDTO {
    private Long id;
    private Long salesHeadUserId;
    private String salesHeadName;
    private Long tenantId;
    private String tenantName;
    private LocalDateTime assignedAt;
}