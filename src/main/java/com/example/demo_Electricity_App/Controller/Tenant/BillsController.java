package com.example.demo_Electricity_App.Controller.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.BillStatusUpdateRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.BillResponseDTO;
import com.example.demo_Electricity_App.Enums.BillStatus;
import com.example.demo_Electricity_App.Service.Tenant.BillsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/bills")
public class BillsController {
    @Autowired
    private BillsService billsService;

    @GetMapping("/{id}")
    public ResponseEntity<BillResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(billsService.getById(id));
    }

    /** Customer views their own bill history from the app. */
    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<BillResponseDTO>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(billsService.getByCustomer(customerId));
    }

    @GetMapping("/by-customer/{customerId}/status")
    public ResponseEntity<List<BillResponseDTO>> getByCustomerAndStatus(
            @PathVariable Long customerId, @RequestParam BillStatus status) {
        return ResponseEntity.ok(billsService.getByCustomerAndStatus(customerId, status));
    }

    /** Marks a bill PAID once payment is confirmed (e.g. via a payment gateway webhook). */
    @PreAuthorize("hasAnyRole('OPERATIONAL_HEAD','BILLER')")
    @PutMapping("/{id}/status")
    public ResponseEntity<BillResponseDTO> updateStatus(
            @PathVariable Long id, @Valid @RequestBody BillStatusUpdateRequestDTO dto) {
        return ResponseEntity.ok(billsService.updateStatus(id, dto));
    }
}
