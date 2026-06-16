package com.example.demo_Electricity_App.Repository.Tenant;

import com.example.demo_Electricity_App.Entity.Master.Tenant;
import com.example.demo_Electricity_App.Entity.Tenant.TenantUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface TenantUsersRepository extends JpaRepository<TenantUsers,Long> {
    Optional<TenantUsers> findByEmail(String username);
    boolean existsBySchemaName(String schemaName);

    boolean existsByEmail(String issuedTo);
}
