package com.example.demo_Electricity_App.DTO.Tenant.Response;

import lombok.Data;

@Data
public class BpoStatesResponseDTO {
    private Long id;
    private String stateName;
    private boolean isActive;
    private Long masterStateId;
    private Long tenantId;
}
