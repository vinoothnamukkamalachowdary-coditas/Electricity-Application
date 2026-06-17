package com.example.demo_Electricity_App.Repository.Master;

import com.example.demo_Electricity_App.Entity.Master.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area,Long> {
    List<Area> findByCityId(Long cityId);
    boolean existsByAreaCode(String areaCode);
}
