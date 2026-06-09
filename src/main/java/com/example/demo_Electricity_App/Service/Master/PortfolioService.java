package com.example.demo_Electricity_App.Service.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.PortfolioRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.PortfolioResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Portfolios;
import com.example.demo_Electricity_App.Entity.Master.Tenant;
import com.example.demo_Electricity_App.Entity.Master.Users;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Master.PortfolioMapper;
import com.example.demo_Electricity_App.Repository.Master.PortfoliosRepository;
import com.example.demo_Electricity_App.Repository.Master.TenantRepository;
import com.example.demo_Electricity_App.Repository.Master.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfoliosRepository repository;
    private final UsersRepository users;
    private final TenantRepository tenants;
    private final PortfolioMapper mapper;

    public PortfolioResponseDTO assignPortfolio(PortfolioRequestDTO portfolioRequestDTO) {
        Users salesHead = users.findById(portfolioRequestDTO.getSalesHeadUserId()).orElseThrow(() -> new ResourceNotFoundException("salesHeadUserId"));
        Tenant tenant = tenants.findById(portfolioRequestDTO.getTenantID()).orElseThrow(() -> new ResourceNotFoundException("tenantID"));
        Portfolios portfolios = new Portfolios();
        portfolios.setSalesHead(salesHead);
        portfolios.setTenant(tenant);
        portfolios.setAssigned_at(LocalDateTime.now());
        return mapper.toResponse(repository.save(portfolios));
    }

    public List<PortfolioResponseDTO> getAssignedPortfolios(Long salesHeadId) {
        return repository.findAll().stream()
                .filter(p -> p.getSalesHead() != null &&
                        p.getSalesHead().getId().equals(salesHeadId))
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<PortfolioResponseDTO> getAllPortfolios() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }
}
