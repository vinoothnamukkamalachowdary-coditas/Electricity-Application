package com.example.demo_Electricity_App.Controller.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.CityRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.CityResponseDTO;
import com.example.demo_Electricity_App.Service.Master.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master/city")
public class CityController {

    @Autowired
    private CityService cityService;

    /** DISTRICT_HEAD creates cities and appoints CITY_HEAD */
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGEMENT_TEAM', 'STATE_HEAD', 'DISTRICT_HEAD')")
    @PostMapping
    public ResponseEntity<CityResponseDTO> createCity(@Valid @RequestBody CityRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.createCity(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponseDTO> getCity(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.getCity(id));
    }

    @GetMapping("/by-district/{districtId}")
    public ResponseEntity<List<CityResponseDTO>> getByDistrict(@PathVariable Long districtId) {
        return ResponseEntity.ok(cityService.getCitiesByDistrict(districtId));
    }

    @GetMapping
    public ResponseEntity<List<CityResponseDTO>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGEMENT_TEAM', 'STATE_HEAD', 'DISTRICT_HEAD')")
    @PutMapping("/{id}")
    public ResponseEntity<CityResponseDTO> updateCity(
            @PathVariable Long id, @Valid @RequestBody CityRequestDTO dto) {
        return ResponseEntity.ok(cityService.updateCity(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGEMENT_TEAM', 'STATE_HEAD', 'DISTRICT_HEAD')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateCity(@PathVariable Long id) {
        cityService.deactivateCity(id);
        return ResponseEntity.noContent().build();
    }
}
