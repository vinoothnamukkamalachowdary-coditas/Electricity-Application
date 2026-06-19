package com.example.demo_Electricity_App.DTO.Tenant.Response;

import lombok.Data;

@Data
public class CustomerResponseDTO {
    private Long id;
    private String customerName;
    private String email;
    private String mobile;
    private String address;
    private Long pincode;
    private Long masterStateId;
    private Long masterDistrictId;
    private Long masterCityId;
    private Long masterServiceAreaId;
    private Long tenantId;
    private String tenantName;
    private Long meterTypeId;
    private String meterTypeName;
}
