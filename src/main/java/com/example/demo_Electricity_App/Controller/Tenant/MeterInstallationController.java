package com.example.demo_Electricity_App.Controller.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.MeterInstallationRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.MeterInstallationResponseDTO;
import com.example.demo_Electricity_App.Service.Tenant.MeterInstallationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/meter-installation")
public class MeterInstallationController {
    @Autowired
    private MeterInstallationService service;

    @PreAuthorize("hasAnyRole('OPERATIONAL_HEAD','TECHNICIAN')")
    @PostMapping
    public ResponseEntity<MeterInstallationResponseDTO> install(
            @Valid @RequestBody MeterInstallationRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.install(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeterInstallationResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<MeterInstallationResponseDTO>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(service.getByCustomer(customerId));
    }

    @PreAuthorize("hasAnyRole('OPERATIONAL_HEAD','TECHNICIAN')")
    @GetMapping("/by-technician/{masterTechnicianId}")
    public ResponseEntity<List<MeterInstallationResponseDTO>> getByTechnician(
            @PathVariable Long masterTechnicianId) {
        return ResponseEntity.ok(service.getByTechnician(masterTechnicianId));
    }
}
