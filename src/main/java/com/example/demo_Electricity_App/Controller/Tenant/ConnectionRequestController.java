package com.example.demo_Electricity_App.Controller.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.ApproveConnectionRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.ConnectionRequestEstablishmentDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.ConnectionRequestResponseDTO;
import com.example.demo_Electricity_App.Enums.ConnectionStatus;
import com.example.demo_Electricity_App.Service.Tenant.ConnectionRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/connection-request")
public class ConnectionRequestController {

    @Autowired
    private ConnectionRequestService service;

    @PreAuthorize("hasAnyRole('CRM','OPERATIONAL_HEAD')")
    @PostMapping
    public ResponseEntity<ConnectionRequestResponseDTO> create(
            @Valid @RequestBody ConnectionRequestEstablishmentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PreAuthorize("hasRole('OPERATIONAL_HEAD')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<ConnectionRequestResponseDTO> approve(
            @PathVariable Long id,
            @RequestParam Long approvedByTenantUserId,
            @Valid @RequestBody ApproveConnectionRequestDTO dto) {
        return ResponseEntity.ok(service.approve(id, approvedByTenantUserId, dto));
    }

    @PreAuthorize("hasRole('OPERATIONAL_HEAD')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<ConnectionRequestResponseDTO> reject(
            @PathVariable Long id,
            @RequestParam Long approvedByTenantUserId,
            @Valid @RequestBody ApproveConnectionRequestDTO dto) {
        return ResponseEntity.ok(service.reject(id, approvedByTenantUserId, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConnectionRequestResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<ConnectionRequestResponseDTO>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(service.getByCustomer(customerId));
    }

    @PreAuthorize("hasRole('OPERATIONAL_HEAD')")
    @GetMapping("/by-status")
    public ResponseEntity<List<ConnectionRequestResponseDTO>> getByStatus(@RequestParam ConnectionStatus status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }
}
