package com.example.demo_Electricity_App.Repository.Tenant;

import com.example.demo_Electricity_App.Entity.Tenant.TenantInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantInvitationRepository extends JpaRepository<TenantInvitation,Long> {
    Optional<TenantInvitation> findByInvitationToken(String invitationToken);
}
