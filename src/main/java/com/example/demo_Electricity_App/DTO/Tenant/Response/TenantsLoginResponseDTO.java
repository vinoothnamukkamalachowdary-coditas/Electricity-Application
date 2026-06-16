package com.example.demo_Electricity_App.DTO.Tenant.Response;

import com.example.demo_Electricity_App.Enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TenantsLoginResponseDTO {
    private String token;
    private String email;
    private Role role;
    private String schemaName;
}
