package com.example.demo_Electricity_App.Repository.Master;

import com.example.demo_Electricity_App.Entity.Master.ServiceArea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ServiceAreaRepository extends JpaRepository<ServiceArea,Long> {
    List<ServiceArea> findByAreaId(Long areaId);
    Optional<ServiceArea> findByTechnicianId(Long technicianId);
    Optional<ServiceArea> findByBillerId(Long billerId);
}
