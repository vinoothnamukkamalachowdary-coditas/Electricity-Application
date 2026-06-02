package com.example.demo_Electricity_App.DTO.Master.Request;

import com.example.demo_Electricity_App.Enums.Master.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvitationRequestDTO {
    @NotBlank(message = "Do not leave blank")
    @Email(message = "enter a valid email address")
    String issuedTo;

    @NotNull(message = "Role must not be null")
    private Role role;

    String message;
}
