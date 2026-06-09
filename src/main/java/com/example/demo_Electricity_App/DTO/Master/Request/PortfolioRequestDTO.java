package com.example.demo_Electricity_App.DTO.Master.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortfolioRequestDTO {
    @NotNull(message ="null values are not accepted")
    private Long salesHeadUserId;

    @NotNull(message = "null values not accepted")
    private Long tenantID;
}
