package com.example.demo_Electricity_App.Entity.Tenant;

import com.example.demo_Electricity_App.Entity.Master.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String customerName;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    private String mobile;

    private String Address;

    @Column(length = 10)
    private Long pincode;

    // geographic fields required during enrollment (cross-schema IDs)
    private Long masterStateId;
    private Long masterDistrictId;
    private Long masterCityId;
    private Long masterServiceAreaId;

    @ManyToOne
    @JoinColumn(name = "tenants_id")
    private Tenants tenants;

    @ManyToOne
    @JoinColumn(name = "meter_type_id")
    private MeterTypes meterType;

    @OneToMany(mappedBy = "bpoState",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Complaints> complaint;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Bills> bill;
}
