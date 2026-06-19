package com.example.demo_Electricity_App.Controller.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.MeterTypesRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.MeterTypesResponseDTO;
import com.example.demo_Electricity_App.Service.Tenant.MeterTypesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/meter-types")
public class MeterTypesController {

    @Autowired
    private MeterTypesService meterTypesService;

    @PreAuthorize("hasRole('OPERATIONAL_HEAD')")
    @PostMapping("/{tenantId}")
    public ResponseEntity<MeterTypesResponseDTO> createMeterType(
            @PathVariable Long tenantId, @Valid @RequestBody MeterTypesRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meterTypesService.createMeterType(tenantId, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeterTypesResponseDTO> getMeterType(@PathVariable Long id) {
        return ResponseEntity.ok(meterTypesService.getMeterType(id));
    }

    @GetMapping
    public ResponseEntity<List<MeterTypesResponseDTO>> getAllMeterTypes() {
        return ResponseEntity.ok(meterTypesService.getAllMeterTypes());
    }

    @PreAuthorize("hasRole('OPERATIONAL_HEAD')")
    @PutMapping("/{id}")
    public ResponseEntity<MeterTypesResponseDTO> updateMeterType(
            @PathVariable Long id, @Valid @RequestBody MeterTypesRequestDTO dto) {
        return ResponseEntity.ok(meterTypesService.updateMeterType(id, dto));
    }
}
