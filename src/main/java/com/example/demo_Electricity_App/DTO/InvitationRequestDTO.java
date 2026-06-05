package com.example.demo_Electricity_App.DTO;

import com.example.demo_Electricity_App.Enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InvitationRequestDTO {
    @NotBlank(message = "Do not leave blank")
    @Email(message = "enter a valid email address")
    private String issuedTo;

    @NotNull(message = "Role must not be null")
    private Role role;

    String message;

    private LocalDateTime issuedAt;
}
