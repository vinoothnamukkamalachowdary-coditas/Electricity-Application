package com.example.demo_Electricity_App.Controller.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.AcceptInvitationRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Request.InvitationRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.InvitationResponseDTO;
import com.example.demo_Electricity_App.Service.Master.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/send/invitation")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService service;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGEMENT_TEAM','STATE_HEAD','DISTRICT_HEAD','CITY_HEAD')")
    @PostMapping("/send")
    public ResponseEntity<InvitationResponseDTO> sendInvitation(
            @Valid @RequestBody InvitationRequestDTO invitationRequestDto,
            @AuthenticationPrincipal UserDetails auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.sendInvitation(invitationRequestDto, auth));
    }

    /**
     * Accept a master-side invitation.
     * The invitee provides their token (from email), name, and chosen password.
     * This endpoint is public — the invitee has no account yet.
     */
    @PostMapping("/accept")
    public ResponseEntity<Void> acceptInvitation(@Valid @RequestBody AcceptInvitationRequestDTO dto) {
        service.acceptInvitation(dto);
        return ResponseEntity.ok().build();
    }

}
