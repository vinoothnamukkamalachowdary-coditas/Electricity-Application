package com.example.demo_Electricity_App.Repository.Tenant;

import com.example.demo_Electricity_App.Entity.Tenant.MeterInstallation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeterInstallationRepository extends JpaRepository<MeterInstallation,Long> {
    List<MeterInstallation> findByCustomersId(Long customerId);

    Optional<MeterInstallation> findByMeterNumber(int meterNumber);

    boolean existsByMeterNumber(int meterNumber);

    List<MeterInstallation> findByMasterTechnicianId(Long masterTechnicianId);

    Optional<MeterInstallation> findByConnectionRequestId(Long connectionRequestId);
}
