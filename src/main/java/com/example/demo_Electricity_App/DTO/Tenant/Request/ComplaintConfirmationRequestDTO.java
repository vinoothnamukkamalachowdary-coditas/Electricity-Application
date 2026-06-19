package com.example.demo_Electricity_App.DTO.Tenant.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ComplaintConfirmationRequestDTO {
    @NotNull(message = "Resolution confirmation flag must not be null")
    private Boolean resolvedByCustomer;

    private String remark;
}
