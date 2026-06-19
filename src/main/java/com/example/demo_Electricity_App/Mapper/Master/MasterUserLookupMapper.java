package com.example.demo_Electricity_App.Mapper.Master;

import com.example.demo_Electricity_App.DTO.Master.Response.MasterUserLookupResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Users;
import org.springframework.stereotype.Component;

@Component
public class MasterUserLookupMapper {
    public MasterUserLookupResponseDTO toResponse(Users user) {
        MasterUserLookupResponseDTO dto = new MasterUserLookupResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.is_Active());
        return dto;
    }
}
