package com.example.demo_Electricity_App.DTO.Master.Response;

import lombok.Data;

@Data
public class DistrictResponseDTO {
    private Long id;
    private String districtName;
    private boolean isActive;
    private Long stateId;
    private String stateName;
    private Long districtHeadId;
    private String districtHeadName;
}
