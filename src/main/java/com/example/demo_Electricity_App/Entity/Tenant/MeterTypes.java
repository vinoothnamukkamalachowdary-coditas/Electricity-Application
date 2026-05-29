package com.example.demo_Electricity_App.Entity.Tenant;

import com.example.demo_Electricity_App.Entity.Master.Tenant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String meter_Type;

    private Long ratePerUnit;

    private Long photoCount;

    private int photo_interval_seconds;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenants tenant;

}
