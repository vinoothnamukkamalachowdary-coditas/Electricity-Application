package com.example.demo_Electricity_App.Mapper.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Response.ConnectionRequestResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.ConnectionRequest;
import org.springframework.stereotype.Component;

@Component
public class ConnectionRequestMapper {
    public ConnectionRequestResponseDTO toResponse(ConnectionRequest request) {
        ConnectionRequestResponseDTO dto = new ConnectionRequestResponseDTO();
        dto.setId(request.getId());
        dto.setStatus(request.getStatus());
        dto.setRequestedAt(request.getRequestedAt());
        dto.setApprovedAt(request.getApprovedAt());
        if (request.getCustomer() != null) {
            dto.setCustomerId(request.getCustomer().getId());
            dto.setCustomerName(request.getCustomer().getCustomerName());
        }
        if (request.getMeterType() != null) {
            dto.setMeterTypeId(request.getMeterType().getId());
            dto.setMeterTypeName(request.getMeterType().getMeterType());
        }
        if (request.getApprovedBy() != null) {
            dto.setApprovedById(request.getApprovedBy().getId());
            dto.setApprovedByName(request.getApprovedBy().getName());
        }
        dto.setMasterServiceAreaId(request.getMasterServiceAreaId());
        return dto;
    }
}
