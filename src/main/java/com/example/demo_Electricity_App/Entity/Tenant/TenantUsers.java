package com.example.demo_Electricity_App.Entity.Tenant;

import com.example.demo_Electricity_App.Entity.Master.Tenant;
import com.example.demo_Electricity_App.Enums.Tenant.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "tenants_id")
    private Tenants tenants;
}
