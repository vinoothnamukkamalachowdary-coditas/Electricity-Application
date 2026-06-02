package com.example.demo_Electricity_App.DTO.Master.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DistrictRequestDTO {
    @NotBlank(message = "District name must not be blank")
    private String districtName;

    @NotNull(message = "State ID must not be null")
    private Long stateId;

    @NotNull(message = "District head user ID must not be null")
    private Long districtHeadId;
}
