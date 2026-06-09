package com.example.demo_Electricity_App.Repository.Master;

import com.example.demo_Electricity_App.Entity.Master.District;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findDistrictByStateId(Long stateId);
    List<District> findByIsActive(boolean active);
}