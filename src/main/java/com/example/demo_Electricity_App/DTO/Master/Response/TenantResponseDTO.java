package com.example.demo_Electricity_App.DTO.Master.Response;

import lombok.Data;

@Data
public class TenantResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String schemaName;
    private boolean active;
}
