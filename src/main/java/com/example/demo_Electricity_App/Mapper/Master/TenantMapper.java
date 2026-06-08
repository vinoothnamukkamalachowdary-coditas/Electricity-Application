package com.example.demo_Electricity_App.Mapper.Master;

import com.example.demo_Electricity_App.DTO.Master.Response.TenantResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Tenant;
import org.springframework.stereotype.Component;

@Component
public class TenantMapper {

        public static TenantResponseDTO
        toResponse(Tenant tenant) {

            TenantResponseDTO dto =
                    new TenantResponseDTO();

            dto.setId(tenant.getId());

            dto.setName(
                    tenant.getName());

            dto.setEmail(
                    tenant.getEmail());

            dto.setSchemaName(
                    tenant.getSchemaName());

            dto.setActive(
                    tenant.isActive());

            return dto;
        }
}
