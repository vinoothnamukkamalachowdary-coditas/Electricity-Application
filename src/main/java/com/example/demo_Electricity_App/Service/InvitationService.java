package com.example.demo_Electricity_App.Service;

import com.example.demo_Electricity_App.DTO.InvitationRequestDTO;
import com.example.demo_Electricity_App.DTO.InvitationResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Invitation;
import com.example.demo_Electricity_App.Entity.Master.Users;
import com.example.demo_Electricity_App.Enums.Role;
import com.example.demo_Electricity_App.Mapper.InvitationMapper;
import com.example.demo_Electricity_App.Repository.InvitationRepository;
import com.example.demo_Electricity_App.Repository.Master.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final UsersRepository usersRepository;
    private final EmailService emailService;
    private final InvitationMapper invitationMapper;

    public InvitationResponseDTO sendInvitation(
            InvitationRequestDTO request,
            UserDetails currentUser
    ) {

        Users issuedBy =
                usersRepository.findByEmail(
                                currentUser.getUsername())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Logged in user not found"));

        String token =
                emailService.onBoardInvitation(
                        request.getIssuedTo());

        Invitation invitation =
                Invitation.builder()
                        .issuedTo(request.getIssuedTo())
                        .role(Role.valueOf(
                                request.getRole().name()))
                        .issuedBy(issuedBy)
                        .invitationToken(token)
                        .issuedAt(LocalDateTime.now())
                        .expiresAt(LocalDateTime.now().plusDays(7))
                        .build();

        Invitation saved =
                invitationRepository.save(invitation);

        return invitationMapper.toResponse(saved);
    }
}