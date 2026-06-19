package com.example.demo_Electricity_App.Service.Tenant;

import com.example.demo_Electricity_App.DTO.Master.Response.MasterUserLookupResponseDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.ComplaintAssignTechnicianRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.ComplaintConfirmationRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.ComplaintMarkFixedRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.ComplaintRaiseRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.ComplaintResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.Complaints;
import com.example.demo_Electricity_App.Entity.Tenant.Customers;
import com.example.demo_Electricity_App.Entity.Tenant.TenantUsers;
import com.example.demo_Electricity_App.Enums.ComplaintsStatus;
import com.example.demo_Electricity_App.Enums.Role;
import com.example.demo_Electricity_App.Exception.InvalidOperationException;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Tenant.ComplaintsMapper;
import com.example.demo_Electricity_App.Repository.Tenant.BpoRepository;
import com.example.demo_Electricity_App.Repository.Tenant.ComplaintsRepository;
import com.example.demo_Electricity_App.Repository.Tenant.CustomersRepository;
import com.example.demo_Electricity_App.Repository.Tenant.TenantUsersRepository;
import com.example.demo_Electricity_App.Service.Master.MasterUserLookupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ComplaintsService {
    private static final int MAX_ESCALATION_LEVEL = 2; // 0=PERSONNEL, 1=MANAGER, 2=HIGHER_MANAGER

    private final ComplaintsRepository complaintsRepository;
    private final CustomersRepository customersRepository;
    private final TenantUsersRepository tenantUsersRepository;
    private final BpoRepository bpoRepository;
    private final MasterUserLookupService masterLookupService;
    private final ComplaintsMapper mapper;

    public ComplaintResponseDTO raise(@Valid ComplaintRaiseRequestDTO dto) {
        Customers customer = customersRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + dto.getCustomerId()));

        Complaints complaint = new Complaints();
        complaint.setBpoState(customer);
        complaint.setTitle(dto.getTitle());
        complaint.setComplaintDescription(dto.getComplaintDescription());
        complaint.setEscalationLevel(0);
        complaint.setRaisedAt(LocalDateTime.now());
        complaint.setLastUpdatedAt(LocalDateTime.now());

        Optional<TenantUsers> personnel = findPersonnelForCustomer(customer);
        if (personnel.isPresent()) {
            complaint.setAssignedPersonnel(personnel.get());
            complaint.setStatus(ComplaintsStatus.ASSIGNED);
        } else {
            complaint.setStatus(ComplaintsStatus.OPEN);
        }

        return mapper.toResponse(complaintsRepository.save(complaint));
    }

    public ComplaintResponseDTO assignTechnician(Long complaintId, @Valid ComplaintAssignTechnicianRequestDTO dto) {
        Complaints complaint = getOrThrow(complaintId);

        if (complaint.getStatus() == ComplaintsStatus.CLOSED || complaint.getStatus() == ComplaintsStatus.RESOLVED) {
            throw new InvalidOperationException(
                    "Complaint " + complaintId + " is already " + complaint.getStatus());
        }

        MasterUserLookupResponseDTO technician =
                masterLookupService.resolveUserWithRole(dto.getMasterTechnicianId(), Role.TECHNICIAN);

        complaint.setMasterTechnicianId(technician.getId());
        complaint.setStatus(ComplaintsStatus.IN_PROGRESS);
        complaint.setLastUpdatedAt(LocalDateTime.now());

        return mapper.toResponse(complaintsRepository.save(complaint));
    }

    public ComplaintResponseDTO markFixedByTechnician(Long complaintId, @Valid ComplaintMarkFixedRequestDTO dto) {
        Complaints complaint = getOrThrow(complaintId);

        if (complaint.getStatus() != ComplaintsStatus.IN_PROGRESS) {
            throw new InvalidOperationException(
                    "Complaint " + complaintId + " must be IN_PROGRESS for a technician to mark it fixed"
                            + " (currently " + complaint.getStatus() + ")");
        }

        if (dto.getRemark() != null && !dto.getRemark().isBlank()) {
            String existing = complaint.getComplaintDescription() == null ? "" : complaint.getComplaintDescription();
            complaint.setComplaintDescription(existing + "\n[Technician]: " + dto.getRemark());
        }
        complaint.setLastUpdatedAt(LocalDateTime.now());

        return mapper.toResponse(complaintsRepository.save(complaint));
    }

    public ComplaintResponseDTO confirmResolution(Long complaintId, @Valid ComplaintConfirmationRequestDTO dto) {
        Complaints complaint = getOrThrow(complaintId);

        if (complaint.getStatus() != ComplaintsStatus.IN_PROGRESS
                && complaint.getStatus() != ComplaintsStatus.ESCALATED) {
            throw new InvalidOperationException(
                    "Complaint " + complaintId + " is not awaiting confirmation (status: "
                            + complaint.getStatus() + ")");
        }

        if (Boolean.TRUE.equals(dto.getResolvedByCustomer())) {
            complaint.setStatus(ComplaintsStatus.RESOLVED);
            complaint.setResolvedAt(LocalDateTime.now());
            complaintsRepository.save(complaint);

            complaint.setStatus(ComplaintsStatus.CLOSED);
        } else {
            escalateOneLevel(complaint);
        }

        if (dto.getRemark() != null && !dto.getRemark().isBlank()) {
            String existing = complaint.getComplaintDescription() == null ? "" : complaint.getComplaintDescription();
            complaint.setComplaintDescription(existing + "\n[Confirmation]: " + dto.getRemark());
        }
        complaint.setLastUpdatedAt(LocalDateTime.now());

        return mapper.toResponse(complaintsRepository.save(complaint));
    }

    public ComplaintResponseDTO getById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    public List<ComplaintResponseDTO> getByCustomer(Long customerId) {
        return complaintsRepository.findByBpoStateId(customerId).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<ComplaintResponseDTO> getByStatus(ComplaintsStatus status) {
        return complaintsRepository.findByStatus(status).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<ComplaintResponseDTO> getByAssignedPersonnel(Long tenantUserId) {
        return complaintsRepository.findByAssignedPersonnelId(tenantUserId).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    private void escalateOneLevel(Complaints complaint) {
        int currentLevel = complaint.getEscalationLevel() == null ? 0 : complaint.getEscalationLevel();

        if (currentLevel >= MAX_ESCALATION_LEVEL) {
            // Already at HIGHER_MANAGER — nowhere further to escalate automatically.
            complaint.setStatus(ComplaintsStatus.ESCALATED);
            return;
        }

        int nextLevel = currentLevel + 1;
        Role nextRole = roleForLevel(nextLevel);

        Customers customer = complaint.getBpoState();
        Optional<TenantUsers> nextAuthority = findAuthorityForCustomer(customer, nextRole);

        complaint.setEscalationLevel(nextLevel);
        complaint.setStatus(ComplaintsStatus.ESCALATED);
        complaint.setMasterTechnicianId(null); // previous technician's assignment is closed out
        nextAuthority.ifPresent(complaint::setAssignedPersonnel);
    }

    private Role roleForLevel(int level) {
        return switch (level) {
            case 0 -> Role.PERSONNEL;
            case 1 -> Role.MANAGER;
            case 2 -> Role.HIGHER_MANAGER;
            default -> throw new InvalidOperationException("No BPO role configured for escalation level " + level);
        };
    }

    private Optional<TenantUsers> findPersonnelForCustomer(Customers customer) {
        return findAuthorityForCustomer(customer, Role.PERSONNEL);
    }

    private Optional<TenantUsers> findAuthorityForCustomer(Customers customer, Role role) {
        if (customer.getMasterDistrictId() == null || customer.getMasterCityId() == null) {
            return Optional.empty();
        }

        return bpoRepository.findByMasterDistrictIdAndMasterCityId(
                        customer.getMasterDistrictId(), customer.getMasterCityId())
                .stream()
                .map(bpo -> bpo.getTenantUser())
                .filter(user -> user != null && user.getRole() == role)
                .findFirst();
    }

    private Complaints getOrThrow(Long id) {
        return complaintsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found: " + id));
    }
}
