package com.example.demo_Electricity_App.Entity.Tenant;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BpoStates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String stateName;

    private boolean isActive;

    @OneToMany(mappedBy = "bpoState",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Bpo> bpo;

    @Column(name = "master_state_id")
    private Long masterStateId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tenant_id")
    private Tenants tenant;

}
