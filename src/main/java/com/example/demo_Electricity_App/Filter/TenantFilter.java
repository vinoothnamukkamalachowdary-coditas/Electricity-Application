package com.example.demo_Electricity_App.Filter;

import com.example.demo_Electricity_App.Tenant.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TenantFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tenant = request.getHeader("X-Tenant-ID");
        if (tenant != null && !tenant.isBlank()) {
            TenantContext.setTenant(tenant);
        }
        try{
            filterChain.doFilter(request, response);
        }finally {
            TenantContext.clearTenant();
        }
    }
}
