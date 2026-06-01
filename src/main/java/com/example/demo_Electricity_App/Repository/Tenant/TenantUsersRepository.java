package com.example.demo_Electricity_App.Repository.Tenant;

import com.example.demo_Electricity_App.Entity.Tenant.TenantUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface TenantUsersRepository extends JpaRepository<TenantUsersRepository,Long> {
    TenantUsers findByEmail(String username);
}
