package com.example.demo_Electricity_App.Controller.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.ComplaintAssignTechnicianRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.ComplaintConfirmationRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.ComplaintMarkFixedRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.ComplaintRaiseRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.ComplaintResponseDTO;
import com.example.demo_Electricity_App.Enums.ComplaintsStatus;
import com.example.demo_Electricity_App.Service.Tenant.ComplaintsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/complaints")
public class ComplaintsController {

    @Autowired
    private ComplaintsService complaintsService;

    @PostMapping
    public ResponseEntity<ComplaintResponseDTO> raise(@Valid @RequestBody ComplaintRaiseRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(complaintsService.raise(dto));
    }

    @PreAuthorize("hasAnyRole('PERSONNEL','MANAGER','HIGHER_MANAGER','OPERATIONAL_HEAD')")
    @PutMapping("/{id}/assign-technician")
    public ResponseEntity<ComplaintResponseDTO> assignTechnician(
            @PathVariable Long id, @Valid @RequestBody ComplaintAssignTechnicianRequestDTO dto) {
        return ResponseEntity.ok(complaintsService.assignTechnician(id, dto));
    }

    @PreAuthorize("hasRole('TECHNICIAN')")
    @PutMapping("/{id}/mark-fixed")
    public ResponseEntity<ComplaintResponseDTO> markFixed(
            @PathVariable Long id, @Valid @RequestBody ComplaintMarkFixedRequestDTO dto) {
        return ResponseEntity.ok(complaintsService.markFixedByTechnician(id, dto));
    }

    @PreAuthorize("hasAnyRole('PERSONNEL','MANAGER','HIGHER_MANAGER','OPERATIONAL_HEAD')")
    @PutMapping("/{id}/confirm-resolution")
    public ResponseEntity<ComplaintResponseDTO> confirmResolution(
            @PathVariable Long id, @Valid @RequestBody ComplaintConfirmationRequestDTO dto) {
        return ResponseEntity.ok(complaintsService.confirmResolution(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(complaintsService.getById(id));
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<ComplaintResponseDTO>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(complaintsService.getByCustomer(customerId));
    }

    @PreAuthorize("hasAnyRole('PERSONNEL','MANAGER','HIGHER_MANAGER','OPERATIONAL_HEAD')")
    @GetMapping("/by-status")
    public ResponseEntity<List<ComplaintResponseDTO>> getByStatus(@RequestParam ComplaintsStatus status) {
        return ResponseEntity.ok(complaintsService.getByStatus(status));
    }

    @GetMapping("/assigned-to/{tenantUserId}")
    public ResponseEntity<List<ComplaintResponseDTO>> getByAssignedPersonnel(@PathVariable Long tenantUserId) {
        return ResponseEntity.ok(complaintsService.getByAssignedPersonnel(tenantUserId));
    }
}
