package com.example.demo_Electricity_App.DTO.Master.Response;

import com.example.demo_Electricity_App.Enums.Role;
import lombok.Data;

@Data
public class UsersResponseDTO {
    private Long id;
    private Role role;
    private String name;
    private String email;
    private boolean isActive;
}
