package com.example.demo_Electricity_App.DTO.Tenant.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConnectionRequestEstablishmentDTO {
    @NotNull(message = "Customer ID must not be null")
    private Long customerId;

    @NotNull(message = "Meter type ID must not be null")
    private Long meterTypeId;
}
