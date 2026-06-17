package com.example.demo_Electricity_App.DTO.Master.Response;

import lombok.Data;

@Data
public class ServiceAreaResponseDTO {
    private Long id;
    private String code;
    private Long areaId;
    private String areaName;
    private Long technicianId;
    private String technicianName;
    private Long billerId;
    private String billerName;
}
