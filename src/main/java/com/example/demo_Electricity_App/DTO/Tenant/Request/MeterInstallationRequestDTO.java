package com.example.demo_Electricity_App.DTO.Tenant.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeterInstallationRequestDTO {
    @NotNull(message = "Connection request ID must not be null")
    private Long connectionRequestId;

    @NotNull(message = "Meter number must not be null")
    private Integer meterNumber;

    @NotNull(message = "Master technician ID must not be null")
    private Long masterTechnicianId;
}
