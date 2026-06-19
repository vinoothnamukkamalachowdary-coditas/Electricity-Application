package com.example.demo_Electricity_App.DTO.Tenant.Response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeterReadingResponseDTO {
    private Long id;
    private Double previousReading;
    private Double currentReading;
    private Double consumedUnits;
    private Double rateSnapshotPerUnit;
    private Long installationId;
    private int meterNumber;
    private LocalDateTime readingDate;
    private Long masterBillerId;
    private Long generatedBillId;
}
