package com.example.demo_Electricity_App.Entity.Tenant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double previousReading;

    private Double currentReading;

    private Double consumedUnits;

    private Double rateSnapshotPerUnit;

    @ManyToOne
    @JoinColumn(name = "installation_id")
    private MeterInstallation installation;

    private LocalDateTime readingDate;

    private Long masterBillerId;
}
