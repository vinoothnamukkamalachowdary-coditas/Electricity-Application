package com.example.demo_Electricity_App.Controller.Tenant;

import com.example.demo_Electricity_App.DTO.Master.Request.LoginRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Request.TenantOnBoardRequestDTO;
import com.example.demo_Electricity_App.Service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/tenant")
public class TenantController {
    @Autowired
    private TenantService service;

    @PostMapping("/register/{invitation}")
    public ResponseEntity<String> onBoard(@RequestBody TenantOnBoardRequestDTO dto, @PathVariable String invitation) {
        return ResponseEntity.ok(service.onBoard(dto,invitation));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(LoginRequestDTO dto){
        return ResponseEntity.ok(service.login(dto));
    }
}
