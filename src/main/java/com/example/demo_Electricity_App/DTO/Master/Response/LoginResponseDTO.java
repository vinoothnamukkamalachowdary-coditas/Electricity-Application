package com.example.demo_Electricity_App.DTO.Master.Response;

import com.example.demo_Electricity_App.Enums.Master.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String accessToken;
    private Role role;
    private String email;
}
