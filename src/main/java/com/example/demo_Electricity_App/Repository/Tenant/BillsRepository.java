package com.example.demo_Electricity_App.Repository.Tenant;

import com.example.demo_Electricity_App.Entity.Tenant.Bills;
import com.example.demo_Electricity_App.Enums.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillsRepository extends JpaRepository<Bills, Long> {
    List<Bills> findByCustomerId(Long customerId);

    List<Bills> findByCustomerIdAndStatus(Long customerId, BillStatus status);

    Optional<Bills> findByMeterReadingId(Long meterReadingId);
}
