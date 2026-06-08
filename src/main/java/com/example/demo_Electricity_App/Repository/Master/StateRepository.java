package com.example.demo_Electricity_App.Repository.Master;

import com.example.demo_Electricity_App.Entity.Master.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StateRepository extends JpaRepository<State,Long> {
    boolean existsByName(String name);
    Optional<State> findByName(String name);
    List<State> findAll();
    List<State> findByIs_Activce(boolean isActive);
}
