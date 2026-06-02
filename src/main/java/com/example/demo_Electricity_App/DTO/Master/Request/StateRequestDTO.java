package com.example.demo_Electricity_App.DTO.Master.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StateRequestDTO {
    @NotBlank(message = "State name must not be blank")
    private String name;

    @NotNull(message = "State head user ID must not be null")
    private Long stateHeadId;
}
