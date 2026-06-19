package com.example.demo_Electricity_App.DTO.Tenant.Response;

import com.example.demo_Electricity_App.Enums.Role;
import lombok.Data;

@Data
public class BpoAssignmentResponseDTO {
    private Long id;
    private Long tenantUserId;
    private String tenantUserName;
    private Role tenantUserRole;
    private Long bpoStateId;
    private String bpoStateName;
    private Long masterDistrictId;
    private String masterDistrictName;
    private Long masterCityId;
    private String masterCityName;
}
