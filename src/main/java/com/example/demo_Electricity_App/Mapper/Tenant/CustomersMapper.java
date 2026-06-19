package com.example.demo_Electricity_App.Mapper.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Response.CustomerResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.Customers;
import org.springframework.stereotype.Component;

@Component
public class CustomersMapper {
    public CustomerResponseDTO toResponse(Customers customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setCustomerName(customer.getCustomerName());
        dto.setEmail(customer.getEmail());
        dto.setMobile(customer.getMobile());
        dto.setAddress(customer.getAddress());
        dto.setPincode(customer.getPincode());
        dto.setMasterStateId(customer.getMasterStateId());
        dto.setMasterDistrictId(customer.getMasterDistrictId());
        dto.setMasterCityId(customer.getMasterCityId());
        dto.setMasterServiceAreaId(customer.getMasterServiceAreaId());
        if (customer.getTenants() != null) {
            dto.setTenantId(customer.getTenants().getId());
            dto.setTenantName(customer.getTenants().getName());
        }
        if (customer.getMeterType() != null) {
            dto.setMeterTypeId(customer.getMeterType().getId());
            dto.setMeterTypeName(customer.getMeterType().getMeterType());
        }
        return dto;
    }
}
