package com.example.demo_Electricity_App.DTO.Master.Response;

import com.example.demo_Electricity_App.Enums.Master.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginResponseDTO {
    private String access_token;
    private String refresh_token;
    private Role role;
    private String email;
}
