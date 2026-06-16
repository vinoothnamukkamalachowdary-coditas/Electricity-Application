package com.example.demo_Electricity_App.DTO.Tenant.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TenantsLoginRequestDTO {
    @Email(message = "Enter valid Email")
    @NotBlank(message = "Email shouldn't be blank")
    private String email;

    @NotBlank(message = "Password should not be empty")
    private String password;
}
