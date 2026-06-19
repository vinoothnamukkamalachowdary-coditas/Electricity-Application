package com.example.demo_Electricity_App.Service.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.BillStatusUpdateRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.BillResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.Bills;
import com.example.demo_Electricity_App.Entity.Tenant.Customers;
import com.example.demo_Electricity_App.Entity.Tenant.MeterReading;
import com.example.demo_Electricity_App.Enums.BillStatus;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Tenant.BillsMapper;
import com.example.demo_Electricity_App.Repository.Tenant.BillsRepository;
import com.example.demo_Electricity_App.Service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BillsService {

    private static final int DUE_IN_DAYS = 15;

    private final BillsRepository billsRepository;
    private final EmailService emailService;
    private final BillsMapper mapper;

    public BillResponseDTO generateFromReading(MeterReading reading, Customers customer) {
        Bills bill = new Bills();
        bill.setTotal_Units(reading.getConsumedUnits());
        bill.setAmount(reading.getConsumedUnits() * reading.getRateSnapshotPerUnit());
        bill.setBillDate(LocalDate.now());
        bill.setDueDate(LocalDate.now().plusDays(DUE_IN_DAYS));
        bill.setStatus(BillStatus.GENERATED);
        bill.setCustomer(customer);
        bill.setMeterReading(reading);

        Bills saved = billsRepository.save(bill);

        emailService.sendBillGeneratedEmail(
                customer.getEmail(), customer.getCustomerName(), saved.getAmount(), saved.getDueDate());

        return mapper.toResponse(saved);
    }

    public BillResponseDTO getById(Long id) {
        return mapper.toResponse(billsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found: " + id)));
    }

    public List<BillResponseDTO> getByCustomer(Long customerId) {
        return billsRepository.findByCustomerId(customerId).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<BillResponseDTO> getByCustomerAndStatus(Long customerId, BillStatus status) {
        return billsRepository.findByCustomerIdAndStatus(customerId, status).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    public BillResponseDTO updateStatus(Long id, @Valid BillStatusUpdateRequestDTO dto) {
        Bills bill = billsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found: " + id));
        bill.setStatus(dto.getStatus());
        return mapper.toResponse(billsRepository.save(bill));
    }
}
