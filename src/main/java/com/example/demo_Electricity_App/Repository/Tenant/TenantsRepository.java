package com.example.demo_Electricity_App.Repository.Tenant;

import com.example.demo_Electricity_App.Entity.Tenant.Tenants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TenantsRepository extends JpaRepository<Tenants, Long> {
    List<Tenants> findByIs_Active(boolean isActive);

    Optional<Tenants> findFirstByOrderByIdAsc();
}
