package com.example.demo_Electricity_App.DTO.Tenant.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BpoStatesRequestDTO {
    @NotBlank(message = "State name must not be blank")
    private String stateName;

    @NotNull(message = "Master state ID must not be null")
    private Long masterStateId;
}
