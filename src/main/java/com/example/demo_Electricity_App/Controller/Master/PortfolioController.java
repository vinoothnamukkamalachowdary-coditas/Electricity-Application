package com.example.demo_Electricity_App.Controller.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.PortfolioRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.PortfolioResponseDTO;
import com.example.demo_Electricity_App.Service.Master.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/master/portfolios")
public class PortfolioController {

    private final PortfolioService service;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGEMENT_TEAM")
    @PostMapping("/assign")
    public ResponseEntity<PortfolioResponseDTO> assignPortfolio(@RequestBody PortfolioRequestDTO portfolioRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.assignPortfolio(portfolioRequestDTO));
    }

    @GetMapping("/of-salesHead/{salesHeadId}")
    public ResponseEntity<List<PortfolioResponseDTO>> getAssignedPortfolios(@PathVariable("salesHeadId") Long salesHeadId) {
        return ResponseEntity.ok(service.getAssignedPortfolios(salesHeadId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PortfolioResponseDTO>> getAllPortfolios() {
        return ResponseEntity.ok(service.getAllPortfolios());
    }
}
