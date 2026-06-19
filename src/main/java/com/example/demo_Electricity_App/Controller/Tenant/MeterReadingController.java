package com.example.demo_Electricity_App.Controller.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.MeterReadingRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.MeterReadingResponseDTO;
import com.example.demo_Electricity_App.Service.Tenant.MeterReadingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/meter-reading")
public class MeterReadingController {

    @Autowired
    private MeterReadingService service;

    @PreAuthorize("hasRole('BILLER')")
    @PostMapping
    public ResponseEntity<MeterReadingResponseDTO> recordReading(
            @Valid @RequestBody MeterReadingRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.recordReading(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeterReadingResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/by-installation/{installationId}")
    public ResponseEntity<List<MeterReadingResponseDTO>> getByInstallation(@PathVariable Long installationId) {
        return ResponseEntity.ok(service.getByInstallation(installationId));
    }
}
