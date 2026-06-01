package com.example.demo_Electricity_App.Entity.Tenant;

import com.example.demo_Electricity_App.Entity.Master.Tenant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String meterType;

    @Min(0)
    private Long ratePerUnit;

    @Min(1)
    private Long photoCount;

    @Min(1)
    private int photo_interval_seconds;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenants tenant;

}
