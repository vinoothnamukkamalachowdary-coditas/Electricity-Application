package com.example.demo_Electricity_App.Mapper.Master;

import com.example.demo_Electricity_App.DTO.Master.Response.AreaResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Area;
import org.springframework.stereotype.Component;

@Component
public class AreaMapper {
    public AreaResponseDTO toResponse(Area area) {
    AreaResponseDTO dto = new AreaResponseDTO();
    dto.setId(area.getId());
    dto.setAreaName(area.getName());
    dto.setAreaCode(area.getAreaCode());
    dto.setActive(area.isActive());
    if (area.getCity() != null) {
        dto.setCityID(area.getCity().getId());
        dto.setCityName(area.getCity().getName());
    }
    return dto;
}
}
