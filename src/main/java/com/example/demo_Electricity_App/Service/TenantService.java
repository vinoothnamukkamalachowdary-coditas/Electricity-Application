package com.example.demo_Electricity_App.Service;

import com.example.demo_Electricity_App.DTO.Master.Request.LoginRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Request.TenantOnBoardRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantService {
    public String onBoard(TenantOnBoardRequestDTO dto,String invitation) {

    }

    public String login(LoginRequestDTO dto) {
    }
}
