package com.example.demo_Electricity_App.Controller.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.BpoStatesRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.BpoStatesResponseDTO;
import com.example.demo_Electricity_App.Service.Tenant.BpoStatesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/bpo-states")
public class BpoStatesController {

    @Autowired
    private BpoStatesService bpoStatesService;

    @PreAuthorize("hasRole('OPERATIONAL_HEAD')")
    @PostMapping
    public ResponseEntity<BpoStatesResponseDTO> createBpoState(@Valid @RequestBody BpoStatesRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bpoStatesService.createBpoState(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BpoStatesResponseDTO> getBpoState(@PathVariable Long id) {
        return ResponseEntity.ok(bpoStatesService.getBpoState(id));
    }

    @GetMapping
    public ResponseEntity<List<BpoStatesResponseDTO>> getAllBpoStates() {
        return ResponseEntity.ok(bpoStatesService.getAllBpoStates());
    }

    @PreAuthorize("hasRole('OPERATIONAL_HEAD')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateBpoState(@PathVariable Long id) {
        bpoStatesService.deactivateBpoState(id);
        return ResponseEntity.noContent().build();
    }
}
