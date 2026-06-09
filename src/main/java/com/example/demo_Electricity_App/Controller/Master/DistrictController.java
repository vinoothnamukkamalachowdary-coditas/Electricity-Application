package com.example.demo_Electricity_App.Controller.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.DistrictRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.DistrictResponseDTO;
import com.example.demo_Electricity_App.Service.Master.DistrictService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master/distircts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService service;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGEMENT_TEAM','STATE_HEAD')")
    @PostMapping("/create")
    public ResponseEntity<DistrictResponseDTO> createDistrict(@Valid @RequestBody DistrictRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createDistrict(dto));
    }

    @GetMapping("/getDistrict/{id}")
    public ResponseEntity<DistrictResponseDTO> getDistrict(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDistrict(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DistrictResponseDTO>> getAllDistrict() {
        return ResponseEntity.ok(service.getAllDistrict());
    }

    @GetMapping("/byState/{stateId}")
    public ResponseEntity<List<DistrictResponseDTO>> getDistrictByState(@PathVariable Long stateId) {
        return ResponseEntity.ok(service.getDistrictByState(stateId));
    }

    @PutMapping("/updateDistrict/{id}")
    public ResponseEntity<DistrictResponseDTO> updateDistrict(@PathVariable Long id, @Valid @RequestBody DistrictRequestDTO dto) {
        return ResponseEntity.ok(service.updateDistrict(id,dto));
    }
}
