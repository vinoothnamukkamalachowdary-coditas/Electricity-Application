package com.example.demo_Electricity_App.DTO.Tenant.Request;

import com.example.demo_Electricity_App.Enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TenantsInvitationRequestDTO {

    @NotBlank(message = "This field should not be blank")
    @Email(message = "Enter valid Email")
    private String issuedTo;

    private Role role;

    private String message;
}
