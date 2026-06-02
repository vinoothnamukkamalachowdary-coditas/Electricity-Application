package com.example.demo_Electricity_App.DTO.Master.Response;

import lombok.Data;

@Data
public class StateResponseDTO {
    private Long id;
    private String name;
    private boolean isActive;
    private Long stateHeadId;
    private String stateHeadName;
}
