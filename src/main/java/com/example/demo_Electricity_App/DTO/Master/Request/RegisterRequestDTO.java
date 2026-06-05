package com.example.demo_Electricity_App.DTO.Master.Request;

import com.example.demo_Electricity_App.Enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "Name must not be blank")
    private String name;

    @Email(message = "Enter a valid email address")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotNull(message = "Role must not be null")
    private Role role;
}
