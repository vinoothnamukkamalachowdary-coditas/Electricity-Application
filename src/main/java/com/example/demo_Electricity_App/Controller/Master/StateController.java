package com.example.demo_Electricity_App.Controller.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.StateRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.StateResponseDTO;
import com.example.demo_Electricity_App.Service.Master.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/master/states")
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @PostMapping
    public ResponseEntity<StateResponseDTO> createState(@RequestBody StateRequestDTO stateRequestDTO){
        return ResponseEntity.ok(stateService.createState(stateRequestDTO));
    }

}
