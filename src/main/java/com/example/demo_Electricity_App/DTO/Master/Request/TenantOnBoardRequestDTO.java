package com.example.demo_Electricity_App.DTO.Master.Request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TenantOnBoardRequestDTO {
    @NotBlank(message = "Company name must not be blank")
    private String name;

    @Email(message = "Enter a valid email address")
    @NotBlank(message = "Email must not be blank")
    private String email;

    /** Schema name (slug) for this tenant — e.g. "mseb", "reliance_power" */
    @NotBlank(message = "Schema name must not be blank")
    private String schemaName;

    // POC (Point of Contact) details from the electricity-provider side
    @NotBlank(message = "POC name must not be blank")
    private String pocName;

    @Email(message = "Enter a valid POC email")
    @NotBlank(message = "POC email must not be blank")
    private String pocEmail;

    @NotBlank(message = "POC password must not be blank")
    private String pocPassword;
}
