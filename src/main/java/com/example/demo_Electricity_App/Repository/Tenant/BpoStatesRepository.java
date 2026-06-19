package com.example.demo_Electricity_App.Repository.Tenant;

import com.example.demo_Electricity_App.Entity.Tenant.BpoStates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BpoStatesRepository extends JpaRepository<BpoStates,Long> {
    Optional<BpoStates> findByMasterStateId(Long masterStateId);

    boolean existsByMasterStateId(Long masterStateId);

    List<BpoStates> findByIsActive(boolean isActive);
}

