package com.example.demo_Electricity_App.Service.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.BpoStatesRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.BpoStatesResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.BpoStates;
import com.example.demo_Electricity_App.Entity.Tenant.Tenants;
import com.example.demo_Electricity_App.Exception.InvalidOperationException;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Tenant.BpoStatesMapper;
import com.example.demo_Electricity_App.Repository.Master.StateRepository;
import com.example.demo_Electricity_App.Repository.Tenant.BpoStatesRepository;
import com.example.demo_Electricity_App.Repository.Tenant.TenantsRepository;
import com.example.demo_Electricity_App.Tenant.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BpoStatesService {
    private static final String MASTER_SCHEMA = "public";

    private final BpoStatesRepository bpoStatesRepository;
    private final TenantsRepository tenantsRepository;
    private final StateRepository stateRepository;
    private final BpoStatesMapper mapper;

    public BpoStatesResponseDTO createBpoState(@Valid BpoStatesRequestDTO dto) {
        Tenants self = tenantsRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active tenant schema has no Tenants record — was onboarding completed?"));

        validateMasterStateExists(dto.getMasterStateId());

        if (bpoStatesRepository.existsByMasterStateId(dto.getMasterStateId())) {
            throw new InvalidOperationException(
                    "A BPO call centre already exists for master state: " + dto.getMasterStateId());
        }

        BpoStates bpoState = new BpoStates();
        bpoState.setStateName(dto.getStateName());
        bpoState.setActive(true);
        bpoState.setMasterStateId(dto.getMasterStateId());
        bpoState.setTenant(self);

        return mapper.toResponse(bpoStatesRepository.save(bpoState));
    }

    public BpoStatesResponseDTO getBpoState(Long id) {
        return mapper.toResponse(bpoStatesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BPO state not found: " + id)));
    }

    public List<BpoStatesResponseDTO> getAllBpoStates() {
        return bpoStatesRepository.findAll().stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    public String deactivateBpoState(Long id) {
        BpoStates bpoState = bpoStatesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BPO state not found: " + id));
        bpoState.setActive(false);
        bpoStatesRepository.save(bpoState);
        return "BPO state deactivated";
    }

    private void validateMasterStateExists(Long masterStateId) {
        String callerTenant = TenantContext.getTenant();
        try {
            TenantContext.setTenant(MASTER_SCHEMA);
            if (stateRepository.findById(masterStateId).isEmpty()) {
                throw new ResourceNotFoundException(
                        "Master state not found: " + masterStateId
                                + " — Coditas does not service this state yet");
            }
        } finally {
            if (callerTenant != null) {
                TenantContext.setTenant(callerTenant);
            } else {
                TenantContext.clearTenant();
            }
        }
    }

}
