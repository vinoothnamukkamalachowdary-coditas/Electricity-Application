package com.example.demo_Electricity_App.Controller.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.AreaRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.AreaResponseDTO;
import com.example.demo_Electricity_App.Service.Master.AreaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGEMENT_TEAM','STATE_HEAD','DISTRICT_HEAD','CITY_HEAD')")
    @PostMapping
    public ResponseEntity<AreaResponseDTO> addArea(@Valid @RequestBody AreaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(areaService.addArea(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaResponseDTO> getAreaById(@PathVariable Long id) {
        return ResponseEntity.ok(areaService.getAreaById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AreaResponseDTO>> getAllAreas() {
        return ResponseEntity.ok(areaService.getAllAreas());
    }

    @GetMapping("/by-city/{cityId}")
    public ResponseEntity<List<AreaResponseDTO>> getAreasByCity(@PathVariable Long cityId) {
        return ResponseEntity.ok(areaService.getAreasByCity(cityId));
    }
}
