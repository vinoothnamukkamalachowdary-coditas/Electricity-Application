package com.example.demo_Electricity_App.Controller.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.TenantsAcceptInvitationRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.TenantsInvitationRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.TenantsInvitationResponseDTO;
import com.example.demo_Electricity_App.Service.Tenant.TenantInvitationService;
import com.example.demo_Electricity_App.Tenant.TenantContext;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/tenant/invitation")
public class TenantsInvitationController {

    @Autowired
    private TenantInvitationService invitationService;

    @PreAuthorize("hasAnyRole('POC','OPERATIONAL_HEAD','HIGHER_MANAGER','MANAGER')")
    @PostMapping("/send")
    public ResponseEntity<TenantsInvitationResponseDTO> sendInvitation(
            @Valid @RequestBody TenantsInvitationRequestDTO request,
            @AuthenticationPrincipal UserDetails currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(invitationService.sendInvitation(request, currentUser));
    }

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptInvitation(
            @Valid @RequestBody TenantsAcceptInvitationRequestDTO dto) {
        String schemaName = TenantContext.getTenant();
        if (schemaName == null || schemaName.isBlank()) {
            throw new RuntimeException("X-Tenant-ID header is required to accept an invitation");
        }
        invitationService.acceptInvitation(dto, schemaName);
        return ResponseEntity.ok().build();
    }


}
