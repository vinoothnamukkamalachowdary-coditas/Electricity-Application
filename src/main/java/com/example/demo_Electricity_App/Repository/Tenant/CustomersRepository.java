package com.example.demo_Electricity_App.Repository.Tenant;

import com.example.demo_Electricity_App.Entity.Tenant.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomersRepository extends JpaRepository<Customers, Long> {
    Optional<Customers> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Customers> findByMasterServiceAreaId(Long masterServiceAreaId);

    List<Customers> findByMasterCityId(Long masterCityId);

    List<Customers> findByMasterDistrictId(Long masterDistrictId);

    List<Customers> findByMasterStateId(Long masterStateId);
}
