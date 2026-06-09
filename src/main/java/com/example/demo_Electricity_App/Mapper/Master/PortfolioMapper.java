package com.example.demo_Electricity_App.Mapper.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.PortfolioRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.PortfolioResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Portfolios;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapper {
    public PortfolioResponseDTO toResponse(Portfolios portfolios){
            PortfolioResponseDTO dto = new PortfolioResponseDTO();
            dto.setId(portfolios.getId());
            if (portfolios.getSalesHead() != null) {
                dto.setSalesHeadUserId(portfolios.getSalesHead().getId());
                dto.setSalesHeadName(portfolios.getSalesHead().getName());
            }
            if (portfolios.getTenant() != null) {
                dto.setTenantId(portfolios.getTenant().getId());
                dto.setTenantName(portfolios.getTenant().getName());
            }
            dto.setAssignedAt(portfolios.getAssigned_at());
            return dto;
        }
}

