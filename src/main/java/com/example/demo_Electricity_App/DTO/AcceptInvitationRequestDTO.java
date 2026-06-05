package com.example.demo_Electricity_App.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AcceptInvitationRequestDTO {
    @NotBlank(message = "Token must not be blank")
    private String token;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Password must not be blank")
    private String password;
}
