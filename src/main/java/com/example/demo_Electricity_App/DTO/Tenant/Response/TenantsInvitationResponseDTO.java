package com.example.demo_Electricity_App.DTO.Tenant.Response;

import com.example.demo_Electricity_App.Enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TenantsInvitationResponseDTO {

    private Long id;
    private String invtationToken;
    private String issuedTo;
    private Role role;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
    private String issuedByEmail;
}
