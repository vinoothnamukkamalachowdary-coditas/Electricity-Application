package com.example.demo_Electricity_App.Controller.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.BpoAssignmentRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.BpoAssignmentResponseDTO;
import com.example.demo_Electricity_App.Service.Tenant.BpoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/bpo")
public class BpoController {

    @Autowired
    private BpoService bpoService;

    /** OPERATIONAL_HEAD / HIGHER_MANAGER / MANAGER staff the call centre district/city-wise. */
    @PreAuthorize("hasAnyRole('OPERATIONAL_HEAD','HIGHER_MANAGER','MANAGER')")
    @PostMapping("/assign")
    public ResponseEntity<BpoAssignmentResponseDTO> assign(@Valid @RequestBody BpoAssignmentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bpoService.assign(dto));
    }

    @GetMapping("/by-state/{bpoStateId}")
    public ResponseEntity<List<BpoAssignmentResponseDTO>> getByBpoState(@PathVariable Long bpoStateId) {
        return ResponseEntity.ok(bpoService.getByBpoState(bpoStateId));
    }

    @GetMapping("/personnel")
    public ResponseEntity<List<BpoAssignmentResponseDTO>> findPersonnelFor(
            @RequestParam Long bpoStateId,
            @RequestParam Long masterDistrictId,
            @RequestParam Long masterCityId) {
        return ResponseEntity.ok(bpoService.findPersonnelFor(bpoStateId, masterDistrictId, masterCityId));
    }
}
