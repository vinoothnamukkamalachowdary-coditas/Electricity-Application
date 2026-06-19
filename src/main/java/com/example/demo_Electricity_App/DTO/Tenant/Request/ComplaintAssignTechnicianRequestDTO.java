package com.example.demo_Electricity_App.DTO.Tenant.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ComplaintAssignTechnicianRequestDTO {
    @NotNull(message = "Master technician ID must not be null")
    private Long masterTechnicianId;
}
