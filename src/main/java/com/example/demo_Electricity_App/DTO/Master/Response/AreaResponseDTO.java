package com.example.demo_Electricity_App.DTO.Master.Response;

import lombok.Data;

@Data
public class AreaResponseDTO {
    private Long id;
    private Long cityID;
    private String areaName;
    private String areaCode;
    private boolean isActive;
    private String cityName;
}

