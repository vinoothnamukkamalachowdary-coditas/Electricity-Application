package com.example.demo_Electricity_App.Service.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.AcceptInvitationRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Request.InvitationRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.InvitationResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Invitation;
import com.example.demo_Electricity_App.Entity.Master.Users;
import com.example.demo_Electricity_App.Enums.Role;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Master.InvitationMapper;
import com.example.demo_Electricity_App.Repository.InvitationRepository;
import com.example.demo_Electricity_App.Repository.Master.UsersRepository;
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
@Slf4j
@RequiredArgsConstructor
@Transactional
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final UsersRepository usersRepository;
    private final EmailService emailService;
    private final InvitationMapper invitationMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Which roles each issuer can invite on the Coditas (master) side:
     *
     * ADMIN           → MANAGEMENT_TEAM
     * MANAGEMENT_TEAM → SALES_TEAM, STATE_HEAD
     * STATE_HEAD      → DISTRICT_HEAD
     * DISTRICT_HEAD   → CITY_HEAD
     * CITY_HEAD       → TECHNICIAN, BILLER
     */
    private static final Map<Role, Set<Role>> ALLOWED_INVITATIONS = Map.of(
            Role.ADMIN,            EnumSet.of(Role.MANAGEMENT_TEAM),
            Role.MANAGEMENT_TEAM,  EnumSet.of(Role.SALES_TEAM, Role.STATE_HEAD),
            Role.STATE_HEAD,       EnumSet.of(Role.DISTRICT_HEAD),
            Role.DISTRICT_HEAD,    EnumSet.of(Role.CITY_HEAD),
            Role.CITY_HEAD,        EnumSet.of(Role.TECHNICIAN, Role.BILLER)
    );

    public InvitationResponseDTO sendInvitation(InvitationRequestDTO request, UserDetails currentUser) {
        Users issuer = usersRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        Role targetRole = request.getRole();
        Set<Role> permitted = ALLOWED_INVITATIONS.getOrDefault(issuer.getRole(), EnumSet.noneOf(Role.class));

        if (!permitted.contains(targetRole)) {
            throw new RuntimeException(
                    "Role " + issuer.getRole() + " is not allowed to invite " + targetRole);
        }

        String token = emailService.onBoardInvitation(request.getIssuedTo());

        Invitation invitation = Invitation.builder()
                .issuedTo(request.getIssuedTo())
                .role(targetRole)
                .issuedBy(issuer)
                .invitationToken(token)
                .issuedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build();

        return invitationMapper.toResponse(invitationRepository.save(invitation));
    }

    /**
     * Accept a master-side invitation and create the Coditas user account.
     * Used by MANAGEMENT_TEAM, SALES_TEAM, STATE_HEAD, DISTRICT_HEAD, CITY_HEAD,
     * TECHNICIAN, BILLER — all Coditas employees joining via invite.
     */
    public void acceptInvitation(AcceptInvitationRequestDTO dto) {
        Invitation invitation = invitationRepository.findByInvitationToken(dto.getToken())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid invitation token"));

        if (invitation.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invitation has expired");
        }

        if (usersRepository.existsByEmail(invitation.getIssuedTo())) {
            throw new RuntimeException("User already registered with this email");
        }

        Users newUser = Users.builder()
                .name(dto.getName())
                .email(invitation.getIssuedTo())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(invitation.getRole())
                .is_Active(true)
                .created_at(LocalDateTime.now())
                .build();

        usersRepository.save(newUser);
        invitationRepository.delete(invitation);

        log.info("User {} accepted invitation and registered as {}", newUser.getEmail(), newUser.getRole());
    }
}