package com.example.demo_Electricity_App.Repository.Tenant;

import com.example.demo_Electricity_App.Entity.Tenant.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MeterReadingRepository extends JpaRepository<MeterReading,Long> {
    List<MeterReading> findByInstallationId(Long installationId);

    Optional<MeterReading> findFirstByInstallationIdOrderByReadingDateDesc(Long installationId);

    List<MeterReading> findByMasterBillerId(Long masterBillerId);
}
