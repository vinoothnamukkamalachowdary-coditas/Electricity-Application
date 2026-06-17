package com.example.demo_Electricity_App.DTO.Master.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceAreaRequestDTO {
    private String code;

    @NotNull(message = "Area ID must not be null")
    private Long areaId;

    @NotNull(message = "Technician user ID must not be null")
    private Long technicianId;

    @NotNull(message = "Biller user ID must not be null")
    private Long billerId;
}
