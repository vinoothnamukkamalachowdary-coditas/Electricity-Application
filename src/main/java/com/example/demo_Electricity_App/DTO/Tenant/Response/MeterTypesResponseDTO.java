package com.example.demo_Electricity_App.DTO.Tenant.Response;

import lombok.Data;

@Data
public class MeterTypesResponseDTO {
    private Long id;
    private String meterType;
    private Long ratePerUnit;
    private Long photoCount;
    private int photoIntervalSeconds;
    private Long tenantId;
    private String tenantName;
}
