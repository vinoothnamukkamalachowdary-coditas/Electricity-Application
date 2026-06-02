package com.example.demo_Electricity_App.Filter;

import com.example.demo_Electricity_App.Service.CustomUserDetailsService;
import com.example.demo_Electricity_App.Tenant.TenantContext;
import com.example.demo_Electricity_App.Util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        String token = extractToken(authHeader);

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            processToken(token);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clearTenant();
        }
    }

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private void processToken(String token) {
        try {
            Claims claims = jwtUtil.parseClaims(token);
            String username = claims.getSubject();
            String tenant   = (String) claims.get("tenant");

            if (tenant != null) {
                TenantContext.setTenant(tenant);
            }

            if (username != null && jwtUtil.isValid(token)) {
                UserDetails userDetails =
                        customUserDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            log.warn("JWT processing failed: {}", e.getMessage());
        }
    }
}
