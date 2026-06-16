package com.example.demo_Electricity_App.Controller.Tenant;

import com.example.demo_Electricity_App.DTO.Master.Request.TenantOnBoardRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.TenantResponseDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.TenantsLoginRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.TenantsLoginResponseDTO;
import com.example.demo_Electricity_App.Service.Tenant.TenantAuthService;
import com.example.demo_Electricity_App.Service.Tenant.TenantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/auth/tenant")
public class TenantsController {
    @Autowired
    private TenantService service;

    @Autowired
    private TenantAuthService authService;

    @PreAuthorize("hasRole('SALES_TEAM')")
    @PostMapping("/onboard")
    public ResponseEntity<TenantResponseDTO> onBoard(@Valid @RequestBody TenantOnBoardRequestDTO dto) throws SQLException {
        return ResponseEntity.status(HttpStatus.OK).body(service.onBoard(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<TenantsLoginResponseDTO> login(@Valid @RequestBody TenantsLoginRequestDTO requestDTO){
        return ResponseEntity.ok(authService.login(requestDTO));
    }

}





