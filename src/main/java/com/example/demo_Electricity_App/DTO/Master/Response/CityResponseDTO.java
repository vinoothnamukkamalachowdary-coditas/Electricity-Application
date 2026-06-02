package com.example.demo_Electricity_App.DTO.Master.Response;

import lombok.Data;

@Data
public class CityResponseDTO {
    private Long id;
    private String name;
    private boolean isActive;
    private Integer districtId;
    private String districtName;
    private Long cityHeadId;
    private String cityHeadName;
}
