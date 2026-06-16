package com.example.demo_Electricity_App.DTO.Tenant.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TenantsAcceptInvitationRequestDTO {
    @NotBlank(message = "Token must not be empty")
    private String token;

    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Password should not be left empty")
    private String password;
}
