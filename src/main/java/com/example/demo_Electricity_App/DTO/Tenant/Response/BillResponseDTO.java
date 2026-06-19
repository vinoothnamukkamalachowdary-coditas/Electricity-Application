package com.example.demo_Electricity_App.DTO.Tenant.Response;

import com.example.demo_Electricity_App.Enums.BillStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BillResponseDTO {
    private Long id;
    private Double totalUnits;
    private Double amount;
    private LocalDate billDate;
    private LocalDate dueDate;
    private BillStatus status;
    private Long customerId;
    private String customerName;
    private String customerEmail;
    private Long meterReadingId;
}
