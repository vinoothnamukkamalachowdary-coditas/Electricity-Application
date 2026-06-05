package com.example.demo_Electricity_App.Controller;

import com.example.demo_Electricity_App.DTO.InvitationRequestDTO;
import com.example.demo_Electricity_App.DTO.InvitationResponseDTO;
import com.example.demo_Electricity_App.Service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/onboard")
    public ResponseEntity<InvitationResponseDTO> sendInvitation(@RequestBody InvitationRequestDTO invitationRequestDto, @AuthenticationPrincipal UserDetails auth) {
        return ResponseEntity.ok(service.sendInvitation(invitationRequestDto,auth));
    }

}
