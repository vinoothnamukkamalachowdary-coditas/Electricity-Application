package com.example.demo_Electricity_App.Controller.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.ServiceAreaRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.ServiceAreaResponseDTO;
import com.example.demo_Electricity_App.Service.Master.ServiceAreaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master/servicearea")
public class ServiceAreaController {

    @Autowired
    private ServiceAreaService service;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGEMENT_TEAM','STATE_HEAD','DISTRICT_HEAD','CITY_HEAD'")
    @PostMapping
    public ResponseEntity<ServiceAreaResponseDTO> createServiceArea(
            @Valid @RequestBody ServiceAreaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createServiceArea(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceAreaResponseDTO> getServiceArea(@PathVariable Long id) {
        return ResponseEntity.ok(service.getServiceArea(id));
    }

    @GetMapping("/by-area/{areaId}")
    public ResponseEntity<List<ServiceAreaResponseDTO>> getByArea(@PathVariable Long areaId) {
        return ResponseEntity.ok(service.getServiceAreasByArea(areaId));
    }

    @GetMapping
    public ResponseEntity<List<ServiceAreaResponseDTO>> getAllServiceAreas() {
        return ResponseEntity.ok(service.getAllServiceAreas());
    }
}
