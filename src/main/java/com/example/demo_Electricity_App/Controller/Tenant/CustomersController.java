package com.example.demo_Electricity_App.Controller.Tenant;
import com.example.demo_Electricity_App.DTO.Tenant.Request.CustomerEnrollmentRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.CustomerResponseDTO;
import com.example.demo_Electricity_App.Service.Tenant.CustomersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/customers")
public class CustomersController {

    @Autowired
    private CustomersService service;

    @PreAuthorize("hasRole('CRM')")
    @PostMapping("/enroll")
    public ResponseEntity<CustomerResponseDTO> enroll(@Valid @RequestBody CustomerEnrollmentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.enroll(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCustomer(id));
    }

    @PreAuthorize("hasAnyRole('CRM','OPERATIONAL_HEAD','BILLER','TECHNICIAN','HIGHER_MANAGER','MANAGER','PERSONNEL')")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok(service.getAllCustomers());
    }

    @PreAuthorize("hasAnyRole('CRM','OPERATIONAL_HEAD','BILLER','TECHNICIAN')")
    @GetMapping("/by-service-area/{masterServiceAreaId}")
    public ResponseEntity<List<CustomerResponseDTO>> getByServiceArea(@PathVariable Long masterServiceAreaId) {
        return ResponseEntity.ok(service.getCustomersByServiceArea(masterServiceAreaId));
    }
}
