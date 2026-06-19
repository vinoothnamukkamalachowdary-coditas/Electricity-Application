package com.example.demo_Electricity_App.Service.Master;

import com.example.demo_Electricity_App.DTO.Master.Response.MasterUserLookupResponseDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.ServiceAreaResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.ServiceArea;
import com.example.demo_Electricity_App.Entity.Master.Users;
import com.example.demo_Electricity_App.Enums.Role;
import com.example.demo_Electricity_App.Exception.InvalidOperationException;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Master.MasterUserLookupMapper;
import com.example.demo_Electricity_App.Mapper.Master.ServiceAreaMapper;
import com.example.demo_Electricity_App.Repository.Master.ServiceAreaRepository;
import com.example.demo_Electricity_App.Repository.Master.UsersRepository;
import com.example.demo_Electricity_App.Tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterUserLookupService {
    private static final String MASTER_SCHEMA = "public";

    private final UsersRepository usersRepository;
    private final ServiceAreaRepository serviceAreaRepository;
    private final MasterUserLookupMapper userLookupMapper;
    private final ServiceAreaMapper serviceAreaMapper;

    //Resolves a master user (technician/biller/etc.) by ID
    public MasterUserLookupResponseDTO resolveUserWithRole(Long masterUserId, Role expectedRole) {
        return runOnMasterSchema(() -> {
            Users user = usersRepository.findById(masterUserId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Master user not found: " + masterUserId));

            if (user.getRole() != expectedRole) {
                throw new InvalidOperationException(
                        "User " + masterUserId + " is a " + user.getRole()
                                + ", expected " + expectedRole);
            }

            if (!user.is_Active()) {
                throw new InvalidOperationException(
                        "User " + masterUserId + " is deactivated and cannot be assigned");
            }

            return userLookupMapper.toResponse(user);
        });
    }

    //Plain lookup, no role check — used for read-only
    public MasterUserLookupResponseDTO resolveUser(Long masterUserId) {
        return runOnMasterSchema(() -> usersRepository.findById(masterUserId)
                .map(userLookupMapper::toResponse)
                .orElse(null));
    }

    //Resolves the ServiceArea a customer was enrolled under, and verifies that the supplied technician/biller is in fact the one assigned to that area
    public ServiceAreaResponseDTO resolveServiceArea(Long masterServiceAreaId) {
        return runOnMasterSchema(() -> {
            ServiceArea serviceArea = serviceAreaRepository.findById(masterServiceAreaId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Service area not found: " + masterServiceAreaId));
            return serviceAreaMapper.toResponse(serviceArea);
        });
    }

    public void assertTechnicianBelongsToServiceArea(Long masterServiceAreaId, Long masterTechnicianId) {
        ServiceAreaResponseDTO serviceArea = resolveServiceArea(masterServiceAreaId);
        if (serviceArea.getTechnicianId() == null
                || !serviceArea.getTechnicianId().equals(masterTechnicianId)) {
            throw new InvalidOperationException(
                    "Technician " + masterTechnicianId
                            + " is not assigned to service area " + masterServiceAreaId);
        }
    }

    public void assertBillerBelongsToServiceArea(Long masterServiceAreaId, Long masterBillerId) {
        ServiceAreaResponseDTO serviceArea = resolveServiceArea(masterServiceAreaId);
        if (serviceArea.getBillerId() == null
                || !serviceArea.getBillerId().equals(masterBillerId)) {
            throw new InvalidOperationException(
                    "Biller " + masterBillerId
                            + " is not assigned to service area " + masterServiceAreaId);
        }
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
