package com.example.demo_Electricity_App.Repository.Tenant;

import com.example.demo_Electricity_App.Entity.Tenant.ConnectionRequest;
import com.example.demo_Electricity_App.Enums.ConnectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {
    List<ConnectionRequest> findByCustomerId(Long customerId);

    List<ConnectionRequest> findByStatus(ConnectionStatus status);

    Optional<ConnectionRequest> findFirstByCustomerIdAndStatus(Long customerId, ConnectionStatus status);
}

