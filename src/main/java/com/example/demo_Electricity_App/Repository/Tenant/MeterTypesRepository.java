package com.example.demo_Electricity_App.Repository.Tenant;

import com.example.demo_Electricity_App.Entity.Tenant.MeterTypes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeterTypesRepository extends JpaRepository<MeterTypes,Long> {
    List<MeterTypes> findByTenantId(Long tenantId);

    Optional<MeterTypes> findByMeterTypeIgnoreCase(String meterType);

    boolean existsByMeterTypeIgnoreCase(String meterType);
}
