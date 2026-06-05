package com.example.demo_Electricity_App.Entity.Tenant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tenants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDateTime on_BoardedAt;

    private boolean is_Active;

    @OneToMany(mappedBy = "tenants",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TenantUsers> tenantUsers;

    @OneToMany(mappedBy = "tenant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MeterTypes> meterType;

    @OneToMany(mappedBy = "tenant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<BpoStates> states;
}
