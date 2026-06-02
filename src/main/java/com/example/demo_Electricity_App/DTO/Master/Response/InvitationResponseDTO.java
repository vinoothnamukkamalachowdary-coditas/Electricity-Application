package com.example.demo_Electricity_App.DTO.Master.Response;

import com.example.demo_Electricity_App.Enums.Master.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvitationResponseDTO {
    private Long id;
    private String invitationToken;
    private String issuedTo;
    private Role role;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
    private String issuedByEmail;
}
