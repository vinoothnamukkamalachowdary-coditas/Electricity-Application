package com.example.demo_Electricity_App.Entity.Tenant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bpo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The BPO personnel / manager / higher-manager assigned here
    @ManyToOne(optional = false)
    @JoinColumn(name = "tenant_user_id")
    private TenantUsers tenantUser;

    // The state-level call centre this assignment belongs to
    @ManyToOne(optional = false)
    @JoinColumn(name = "bpo_state_id")
    private BpoStates bpoState;

    private Long masterDistrictId;
    private Long masterCityId;

}
