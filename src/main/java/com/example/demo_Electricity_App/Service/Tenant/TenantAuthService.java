package com.example.demo_Electricity_App.Service.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Request.TenantsLoginRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.TenantsLoginResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.TenantUsers;
import com.example.demo_Electricity_App.Repository.Tenant.TenantUsersRepository;
import com.example.demo_Electricity_App.Util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TenantAuthService {
    private final TenantUsersRepository tenantUsersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public TenantsLoginResponseDTO login(@Valid TenantsLoginRequestDTO requestDTO) {
        TenantUsers user = tenantUsersRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token = jwtUtil.generateTenantToken(
                user.getEmail(),
                user.getRole().name(),
                user.getSchemaName()
        );

        return TenantsLoginResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .schemaName(user.getSchemaName())
                .build();
    }
}
