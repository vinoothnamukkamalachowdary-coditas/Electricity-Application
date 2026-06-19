package com.example.demo_Electricity_App.DTO.Tenant.Response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MeterInstallationResponseDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private int meterNumber;
    private Long masterTechnicianId;
    private String masterTechnicianName;
    private Long connectionRequestId;
    private LocalDate installationDate;
}
