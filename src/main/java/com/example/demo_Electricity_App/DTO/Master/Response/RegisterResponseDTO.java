package com.example.demo_Electricity_App.DTO.Master.Response;

import com.example.demo_Electricity_App.Enums.Master.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterResponseDTO {
    private Long id;
    private String name;
    private String email;
}
