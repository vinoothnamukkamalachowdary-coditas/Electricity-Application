package com.example.demo_Electricity_App.DTO.Master.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CityRequestDTO {
    @NotBlank(message = "City name must not be blank")
    private String name;

    @NotNull(message = "District ID must not be null")
    private Long districtId;

    @NotNull(message = "City head user ID must not be null")
    private Long cityHeadId;
}
