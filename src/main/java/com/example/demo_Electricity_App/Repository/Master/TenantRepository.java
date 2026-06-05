package com.example.demo_Electricity_App.Repository.Master;

import com.example.demo_Electricity_App.Entity.Master.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant,Long> {
    Optional<Tenant> findByEmail(
            String email);

    Optional<Tenant> findBySchemaName(
            String schemaName);

    boolean existsBySchemaName(
            String schemaName);
}
