package com.example.demo_Electricity_App.DTO.Master.Response;

import lombok.Data;

@Data
public class ProviderComparisonResponseDTO {
    private Long tenantId;
    private String tenantName;
    private Long meterTypeId;
    private String meterType;
    private Long ratePerUnit;
    private Long photoCount;
    private int photoIntervalSeconds;
}
