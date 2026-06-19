package com.example.demo_Electricity_App.Repository.Tenant;

import com.example.demo_Electricity_App.Entity.Tenant.Complaints;
import com.example.demo_Electricity_App.Enums.ComplaintsStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintsRepository extends JpaRepository<Complaints, Long> {
    List<Complaints> findByBpoStateId(Long customerId);

    List<Complaints> findByStatus(ComplaintsStatus status);

    List<Complaints> findByAssignedPersonnelId(Long assignedPersonnelId);

    List<Complaints> findByMasterTechnicianId(Long masterTechnicianId);

    List<Complaints> findByEscalationLevelGreaterThanEqual(Integer escalationLevel);
}
