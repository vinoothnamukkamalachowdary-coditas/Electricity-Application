package com.example.demo_Electricity_App.DTO.Tenant.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ComplaintRaiseRequestDTO {
    @NotNull(message = "Customer ID must not be null")
    private Long customerId;

    @NotBlank(message = "Title must not be blank")
    private String title;

    private String complaintDescription;
}
