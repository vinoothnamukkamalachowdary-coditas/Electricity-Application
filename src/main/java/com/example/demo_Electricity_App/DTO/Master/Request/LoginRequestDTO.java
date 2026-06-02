package com.example.demo_Electricity_App.DTO.Master.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @Email(message = "Enter a valid email")
    @NotBlank(message = "Email should not be blank")
    private String email;

    @NotBlank(message = "passoword is not blank")
    private String password;
}
