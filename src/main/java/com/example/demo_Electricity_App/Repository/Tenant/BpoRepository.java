package com.example.demo_Electricity_App.Repository.Tenant;

import com.example.demo_Electricity_App.Entity.Tenant.Bpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BpoRepository extends JpaRepository<Bpo,Long> {
    List<Bpo> findByBpoStateId(Long bpoStateId);

    List<Bpo> findByMasterDistrictId(Long masterDistrictId);

    List<Bpo> findByMasterCityId(Long masterCityId);

    List<Bpo> findByMasterDistrictIdAndMasterCityId(Long masterDistrictId, Long masterCityId);

    List<Bpo> findByBpoStateIdAndMasterDistrictIdAndMasterCityId(
            Long bpoStateId, Long masterDistrictId, Long masterCityId);

    Optional<Bpo> findByTenantUserId(Long tenantUserId);
}
