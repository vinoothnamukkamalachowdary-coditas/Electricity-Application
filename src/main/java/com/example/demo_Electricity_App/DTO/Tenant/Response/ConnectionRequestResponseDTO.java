package com.example.demo_Electricity_App.DTO.Tenant.Response;

import com.example.demo_Electricity_App.Enums.ConnectionStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConnectionRequestResponseDTO {
    private Long id;
    private ConnectionStatus status;
    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;
    private Long customerId;
    private String customerName;
    private Long meterTypeId;
    private String meterTypeName;
    private Long approvedById;
    private String approvedByName;
    private Long masterServiceAreaId;
}
