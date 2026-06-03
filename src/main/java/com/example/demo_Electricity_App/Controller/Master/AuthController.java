package com.example.demo_Electricity_App.Controller.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.LoginRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Request.RegisterRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.LoginResponseDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.RegisterResponseDTO;
import com.example.demo_Electricity_App.Service.Master.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/master")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.login(dto));
    }
}
