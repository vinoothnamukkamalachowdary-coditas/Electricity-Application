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

    private String name;

    private LocalDateTime on_BoardedAt;

    private boolean is_Active;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<TenantUsers> tenantUsers;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<MeterTypes> meterType;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Bpo> bpoList;
}
