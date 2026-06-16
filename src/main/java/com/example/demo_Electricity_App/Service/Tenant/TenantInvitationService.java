package com.example.demo_Electricity_App.Service.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.TenantsAcceptInvitationRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.TenantsInvitationRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.TenantsInvitationResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.TenantInvitation;
import com.example.demo_Electricity_App.Entity.Tenant.TenantUsers;
import com.example.demo_Electricity_App.Enums.Role;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Tenant.TenantInvitationMapper;
import com.example.demo_Electricity_App.Repository.Tenant.TenantInvitationRepository;
import com.example.demo_Electricity_App.Repository.Tenant.TenantUsersRepository;
import com.example.demo_Electricity_App.Service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TenantInvitationService {
    private final TenantInvitationRepository tenantInvitationRepository;
    private final TenantUsersRepository tenantUsersRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final TenantInvitationMapper mapper;

    /**
     * Tenant-side invitation rules:
     *
     * POC              → OPERATIONAL_HEAD
     *                    (POC of MSEB invites the Operational Head who configures everything)
     * OPERATIONAL_HEAD → HIGHER_MANAGER
     *                    (Operational Head creates state-level BPO staff)
     * HIGHER_MANAGER   → MANAGER
     * MANAGER          → PERSONNEL
     */
    private static final Map<Role, Set<Role>> TENANT_ALLOWED_INVITATIONS = Map.of(
            Role.POC,              EnumSet.of(Role.OPERATIONAL_HEAD),
            Role.OPERATIONAL_HEAD, EnumSet.of(Role.HIGHER_MANAGER),
            Role.HIGHER_MANAGER,   EnumSet.of(Role.MANAGER),
            Role.MANAGER,          EnumSet.of(Role.PERSONNEL)
    );

    public TenantsInvitationResponseDTO sendInvitation(
            TenantsInvitationRequestDTO request,
            UserDetails currentUser) {

        TenantUsers issuer = tenantUsersRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Logged-in tenant user not found"));

        Role targetRole = request.getRole();
        Set<Role> permitted = TENANT_ALLOWED_INVITATIONS.getOrDefault(
                issuer.getRole(), EnumSet.noneOf(Role.class));

        if (!permitted.contains(targetRole)) {
            throw new RuntimeException(
                    "Role " + issuer.getRole() + " is not permitted to invite " + targetRole);
        }

        String token = emailService.onBoardInvitation(request.getIssuedTo());

        TenantInvitation invitation = TenantInvitation.builder()
                .issuedTo(request.getIssuedTo())
                .role(targetRole)
                .issuedBy(issuer)
                .invitationToken(token)
                .issuedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build();

        TenantInvitation saved = tenantInvitationRepository.save(invitation);
        return mapper.toResponse(saved);
    }

    /**
     * Accept a tenant-side invitation and create the BPO user account.
     * Requires the correct X-Tenant-ID header so the tenant schema is active.
     */
    public void acceptInvitation(TenantsAcceptInvitationRequestDTO dto, String schemaName) {
        TenantInvitation invitation = tenantInvitationRepository
                .findByInvitationToken(dto.getToken())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid invitation token"));

        if (invitation.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invitation has expired");
        }

        if (tenantUsersRepository.existsByEmail(invitation.getIssuedTo())) {
            throw new RuntimeException("User already registered with this email");
        }

        TenantUsers newUser = TenantUsers.builder()
                .name(dto.getName())
                .email(invitation.getIssuedTo())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(invitation.getRole())
                .schemaName(schemaName)
                .build();

        tenantUsersRepository.save(newUser);
        tenantInvitationRepository.delete(invitation);

        log.info("Tenant user {} accepted invitation and registered as {}",
                newUser.getEmail(), newUser.getRole());
    }
}
