package com.example.demo_Electricity_App.Service.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.BpoAssignmentRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.BpoAssignmentResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.City;
import com.example.demo_Electricity_App.Entity.Master.District;
import com.example.demo_Electricity_App.Entity.Tenant.Bpo;
import com.example.demo_Electricity_App.Entity.Tenant.BpoStates;
import com.example.demo_Electricity_App.Entity.Tenant.TenantUsers;
import com.example.demo_Electricity_App.Enums.Role;
import com.example.demo_Electricity_App.Exception.InvalidOperationException;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Tenant.BpoMapper;
import com.example.demo_Electricity_App.Repository.Master.CityRepository;
import com.example.demo_Electricity_App.Repository.Master.DistrictRepository;
import com.example.demo_Electricity_App.Repository.Tenant.BpoRepository;
import com.example.demo_Electricity_App.Repository.Tenant.BpoStatesRepository;
import com.example.demo_Electricity_App.Repository.Tenant.TenantUsersRepository;
import com.example.demo_Electricity_App.Tenant.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BpoService {
    private static final String MASTER_SCHEMA = "public";

    private static final Set<Role> BPO_ASSIGNABLE_ROLES =
            EnumSet.of(Role.HIGHER_MANAGER, Role.MANAGER, Role.PERSONNEL);

    private final BpoRepository bpoRepository;
    private final BpoStatesRepository bpoStatesRepository;
    private final TenantUsersRepository tenantUsersRepository;
    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;
    private final BpoMapper mapper;

    public BpoAssignmentResponseDTO assign(@Valid BpoAssignmentRequestDTO dto) {
        TenantUsers tenantUser = tenantUsersRepository.findById(dto.getTenantUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant user not found: " + dto.getTenantUserId()));

        if (!BPO_ASSIGNABLE_ROLES.contains(tenantUser.getRole())) {
            throw new InvalidOperationException(
                    "User " + dto.getTenantUserId() + " has role " + tenantUser.getRole()
                            + " — only HIGHER_MANAGER, MANAGER or PERSONNEL can staff a BPO");
        }

        BpoStates bpoState = bpoStatesRepository.findById(dto.getBpoStateId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "BPO state not found: " + dto.getBpoStateId()));

        District district = lookupMasterDistrict(dto.getMasterDistrictId());
        City city = lookupMasterCity(dto.getMasterCityId());

        if (city.getDistrict() == null || !city.getDistrict().getId().equals(district.getId())) {
            throw new InvalidOperationException(
                    "City " + dto.getMasterCityId() + " does not belong to district " + dto.getMasterDistrictId());
        }

        Bpo bpo = new Bpo();
        bpo.setTenantUser(tenantUser);
        bpo.setBpoState(bpoState);
        bpo.setMasterDistrictId(dto.getMasterDistrictId());
        bpo.setMasterCityId(dto.getMasterCityId());

        Bpo saved = bpoRepository.save(bpo);
        return enrich(mapper.toResponse(saved), district, city);
    }

    public List<BpoAssignmentResponseDTO> getByBpoState(Long bpoStateId) {
        return bpoRepository.findByBpoStateId(bpoStateId).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<BpoAssignmentResponseDTO> findPersonnelFor(
            Long bpoStateId, Long masterDistrictId, Long masterCityId) {
        return bpoRepository
                .findByBpoStateIdAndMasterDistrictIdAndMasterCityId(bpoStateId, masterDistrictId, masterCityId)
                .stream()
                .filter(b -> b.getTenantUser() != null && b.getTenantUser().getRole() == Role.PERSONNEL)
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    private District lookupMasterDistrict(Long masterDistrictId) {
        return runOnMasterSchema(() -> districtRepository.findById(masterDistrictId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Master district not found: " + masterDistrictId)));
    }

    private City lookupMasterCity(Long masterCityId) {
        return runOnMasterSchema(() -> cityRepository.findById(masterCityId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Master city not found: " + masterCityId)));
    }

    private BpoAssignmentResponseDTO enrich(BpoAssignmentResponseDTO dto, District district, City city) {
        dto.setMasterDistrictName(district.getDistrictName());
        dto.setMasterCityName(city.getName());
        return dto;
    }

    private <T> T runOnMasterSchema(java.util.function.Supplier<T> action) {
        String callerTenant = TenantContext.getTenant();
        try {
            TenantContext.setTenant(MASTER_SCHEMA);
            return action.get();
        } finally {
            if (callerTenant != null) {
                TenantContext.setTenant(callerTenant);
            } else {
                TenantContext.clearTenant();
            }
        }
    }
}
