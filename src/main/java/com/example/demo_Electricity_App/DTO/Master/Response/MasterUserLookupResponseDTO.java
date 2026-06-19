package com.example.demo_Electricity_App.DTO.Master.Response;

import com.example.demo_Electricity_App.Enums.Role;
import lombok.Data;

@Data
public class MasterUserLookupResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private boolean isActive;
}
