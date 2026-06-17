package com.example.demo_Electricity_App.DTO.Master.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AreaRequestDTO {
    @NotBlank(message = "Don't Leave the AreaCode blank")
    private String areaCode;

    @NotBlank(message = "Do not leave empty")
    private Long cityID;

    @NotBlank(message = "Should not be blank")
    private String areaName;
}
