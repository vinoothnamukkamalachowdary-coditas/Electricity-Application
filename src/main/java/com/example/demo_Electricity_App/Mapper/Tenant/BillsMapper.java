package com.example.demo_Electricity_App.Mapper.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Response.BillResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.Bills;
import org.springframework.stereotype.Component;

@Component
public class BillsMapper {
    public BillResponseDTO toResponse(Bills bill) {
        BillResponseDTO dto = new BillResponseDTO();
        dto.setId(bill.getId());
        dto.setTotalUnits(bill.getTotal_Units());
        dto.setAmount(bill.getAmount());
        dto.setBillDate(bill.getBillDate());
        dto.setDueDate(bill.getDueDate());
        dto.setStatus(bill.getStatus());
        if (bill.getCustomer() != null) {
            dto.setCustomerId(bill.getCustomer().getId());
            dto.setCustomerName(bill.getCustomer().getCustomerName());
            dto.setCustomerEmail(bill.getCustomer().getEmail());
        }
        if (bill.getMeterReading() != null) {
            dto.setMeterReadingId(bill.getMeterReading().getId());
        }
        return dto;
    }
}
