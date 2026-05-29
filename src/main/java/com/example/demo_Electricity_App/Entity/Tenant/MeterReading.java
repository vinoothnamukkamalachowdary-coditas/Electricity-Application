package com.example.demo_Electricity_App.Entity.Tenant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double previous_Readings;

    private Double current_Readings;

    private Double consumed_Units;

    private Double reading_Rate;

    @ManyToOne
    @JoinColumn(name = "installation_id")
    private MeterInstallation installation;
}
