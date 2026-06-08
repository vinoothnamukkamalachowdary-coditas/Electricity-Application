package com.example.demo_Electricity_App.Service.Tenant;
import com.example.demo_Electricity_App.DTO.Master.Request.TenantOnBoardRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.TenantResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Tenant;
import com.example.demo_Electricity_App.Entity.Tenant.TenantUsers;
import com.example.demo_Electricity_App.Mapper.Master.TenantMapper;
import com.example.demo_Electricity_App.Repository.Master.TenantRepository;
import com.example.demo_Electricity_App.Repository.Tenant.TenantUsersRepository;
import com.example.demo_Electricity_App.Service.FlywayService;
import com.example.demo_Electricity_App.Tenant.TenantContext;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class TenantService {

     private final TenantRepository tenantRepository;
     private final TenantUsersRepository repo;
     private final FlywayService flyway;
     private final JdbcTemplate jdbcTemplate;
     private final PasswordEncoder passwordEncoder;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public TenantResponseDTO onBoard(
            TenantOnBoardRequestDTO request) throws SQLException {

        if (repo.existsBySchemaName(
                request.getSchemaName())) {

            throw new RuntimeException(
                    "Schema already exists");
        }

        Tenant tenant = new Tenant();

        tenant.setName(
                request.getName());

        tenant.setEmail(
                request.getEmail());

        tenant.setSchemaName(
                request.getSchemaName());

        tenant.setActive(true);

        tenantRepository.save(tenant);

        createSchema(
                request.getSchemaName());

        flyway.migrate(
                request.getSchemaName(),
                url,
                username,
                password
        );

        createTenantPocUser(
                request);

        return TenantMapper.toResponse(
                tenant);
    }

    private void createSchema(
            String schema) throws SQLException {

        jdbcTemplate.execute(
                "CREATE SCHEMA IF NOT EXISTS "
                        + schema);
    }

    private void createTenantPocUser(
            TenantOnBoardRequestDTO request) {

        TenantContext.setTenant(
                request.getSchemaName());

        TenantUsers user =
                new TenantUsers();

        user.setName(
                request.getPocName());

        user.setEmail(
                request.getPocEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPocPassword()
                ));

        user.setRole(
                com.example.demo_Electricity_App
                        .Enums.Role
                        .OPERATIONAL_HEAD
        );

        repo.save(user);

        TenantContext.clearTenant();
    }

}




