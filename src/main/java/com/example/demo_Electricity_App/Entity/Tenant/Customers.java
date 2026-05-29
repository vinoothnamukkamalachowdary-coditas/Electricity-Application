package com.example.demo_Electricity_App.Entity.Tenant;

import com.example.demo_Electricity_App.Entity.Master.Users;
import jakarta.persistence.*;
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

    private String customerName;

    private String email;

    private Long mobile;

    private String Address;

    private Long pincode;

    @ManyToOne
    @JoinColumn(name = "tenants_id")
    private Tenants tenants;

    @ManyToOne
    @JoinColumn(name = "meter_type_id")
    private MeterTypes meterType;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Complaints> complaint;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Bills> bill;
}
