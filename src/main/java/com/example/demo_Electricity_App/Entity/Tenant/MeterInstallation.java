package com.example.demo_Electricity_App.Entity.Tenant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterInstallation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customers_id")
    private Customers customers;

    private int meterNumber;

    private Long technician_id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<MeterReading> readings;

    private LocalDate installationDate;
}
