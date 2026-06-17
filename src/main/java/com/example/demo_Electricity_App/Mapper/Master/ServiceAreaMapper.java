package com.example.demo_Electricity_App.Mapper.Master;

import com.example.demo_Electricity_App.DTO.Master.Response.ServiceAreaResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.ServiceArea;
import org.springframework.stereotype.Component;

@Component
public class ServiceAreaMapper {
    public ServiceAreaResponseDTO toResponse(ServiceArea sa) {
        ServiceAreaResponseDTO dto = new ServiceAreaResponseDTO();
        dto.setId(sa.getId());
        dto.setCode(sa.getCode());
        if (sa.getArea() != null) {
            dto.setAreaId(sa.getArea().getId());
            dto.setAreaName(sa.getArea().getName());
        }
        if (sa.getTechnician() != null) {
            dto.setTechnicianId(sa.getTechnician().getId());
            dto.setTechnicianName(sa.getTechnician().getName());
        }
        if (sa.getBiller() != null) {
            dto.setBillerId(sa.getBiller().getId());
            dto.setBillerName(sa.getBiller().getName());
        }
        return dto;
    }
}
