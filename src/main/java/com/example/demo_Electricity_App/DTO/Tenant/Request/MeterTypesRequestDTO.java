package com.example.demo_Electricity_App.DTO.Tenant.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeterTypesRequestDTO {
    @NotBlank(message = "Meter type must not be blank")
    private String meterType;

    @NotNull(message = "Rate per unit must not be null")
    @Min(value = 0, message = "Rate per unit cannot be negative")
    private Long ratePerUnit;

    @NotNull(message = "Photo count must not be null")
    @Min(value = 1, message = "At least 1 photo is required for bill generation")
    private Long photoCount;

    @NotNull(message = "Photo interval must not be null")
    @Min(value = 1, message = "Photo interval must be at least 1 second")
    private Integer photoIntervalSeconds;
}
