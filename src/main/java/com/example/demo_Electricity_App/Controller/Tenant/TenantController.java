package com.example.demo_Electricity_App.Controller.Tenant;

import com.example.demo_Electricity_App.DTO.Master.Request.TenantOnBoardRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.TenantResponseDTO;
import com.example.demo_Electricity_App.Service.Tenant.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/auth/tenant")
public class TenantController {
    @Autowired
    private TenantService service;

    @PostMapping("/register")
    public ResponseEntity<TenantResponseDTO> onBoard(@RequestBody TenantOnBoardRequestDTO dto) throws SQLException {
        return ResponseEntity.ok(service.onBoard(dto));
    }

}
