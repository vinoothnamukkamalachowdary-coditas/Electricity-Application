package com.example.demo_Electricity_App.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class InvitationRequestDTO {
    @NotBlank
    @Email
    String issuedTo;

    String message;
}
