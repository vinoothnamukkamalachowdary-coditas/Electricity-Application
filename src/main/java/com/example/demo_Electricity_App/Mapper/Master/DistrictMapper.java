package com.example.demo_Electricity_App.Mapper.Master;

import com.example.demo_Electricity_App.DTO.Master.Response.DistrictResponseDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.StateResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.District;
import com.example.demo_Electricity_App.Entity.Master.State;
import org.springframework.stereotype.Component;

@Component
public class DistrictMapper {
    public DistrictResponseDTO toResponse(District district){
        DistrictResponseDTO dto = new DistrictResponseDTO();
        dto.setId(district.getId());
        dto.setDistrictName(district.getDistrictName());
        dto.setDistrictHeadId(district.getDistrictHead().getId());
        dto.setActive(true);
        dto.setStateId(district.getState().getId());
        dto.setStateName(district.getState().getName());
        dto.setDistrictHeadName(district.getDistrictHead().getName());
        return dto;
    }
}
