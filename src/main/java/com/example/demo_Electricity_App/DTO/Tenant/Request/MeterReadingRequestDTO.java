package com.example.demo_Electricity_App.DTO.Tenant.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeterReadingRequestDTO {
    @NotNull(message = "Installation ID must not be null")
    private Long installationId;

    @NotNull(message = "Current reading must not be null")
    private Double currentReading;

    @NotNull(message = "Master biller ID must not be null")
    private Long masterBillerId;

    @NotEmpty(message = "Photo timestamps must not be empty")
    private List<LocalDateTime> photoTimestamps;
}
