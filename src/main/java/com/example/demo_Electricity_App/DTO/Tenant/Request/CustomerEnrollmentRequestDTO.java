package com.example.demo_Electricity_App.DTO.Tenant.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerEnrollmentRequestDTO {
    @NotBlank(message = "Customer name must not be blank")
    private String customerName;

    @Email(message = "Enter a valid email")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Mobile number must not be blank")
    private String mobile;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @NotNull(message = "Pincode must not be null")
    private Long pincode;

    @NotNull(message = "State must be selected")
    private Long masterStateId;

    @NotNull(message = "District must be selected")
    private Long masterDistrictId;

    @NotNull(message = "City must be selected")
    private Long masterCityId;

    @NotNull(message = "Service area must be selected")
    private Long masterServiceAreaId;

    @NotNull(message = "Tenant (electricity provider) ID must not be null")
    private Long tenantId;

    @NotNull(message = "Meter type must be selected")
    private Long meterTypeId;
}
