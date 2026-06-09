package com.example.demo_Electricity_App.Controller.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.StateRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.StateResponseDTO;
import com.example.demo_Electricity_App.Service.Master.StateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master/states")
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGEMENT_TEAM')")
    @PostMapping("/stateHead")
    public ResponseEntity<StateResponseDTO> createState(@RequestBody StateRequestDTO stateRequestDTO){
        return ResponseEntity.ok(stateService.createState(stateRequestDTO));
    }

    @GetMapping("/getstate/{id}")
    public ResponseEntity<StateResponseDTO> getState(@RequestParam("stateId") Long stateId){
        return ResponseEntity.ok(stateService.getState(stateId));
    }

    @GetMapping("/getAllStates")
    public ResponseEntity<List<StateResponseDTO>> getAllStates(){
        return ResponseEntity.ok(stateService.getAllStates());
    }

    @PutMapping("/updateState/{id}")
    public ResponseEntity<StateResponseDTO> updateState(@PathVariable Long id, @Valid @RequestBody StateRequestDTO stateRequestDTO){
        return ResponseEntity.ok(stateService.updateState(id,stateRequestDTO));
    }



}
