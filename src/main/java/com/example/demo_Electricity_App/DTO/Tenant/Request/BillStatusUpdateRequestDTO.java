package com.example.demo_Electricity_App.DTO.Tenant.Request;

import com.example.demo_Electricity_App.Enums.BillStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BillStatusUpdateRequestDTO {
    @NotNull(message = "Status must not be null")
    private BillStatus status;
}
