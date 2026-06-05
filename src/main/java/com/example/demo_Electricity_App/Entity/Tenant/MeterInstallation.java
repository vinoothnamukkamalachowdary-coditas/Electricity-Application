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

    @Column(nullable = false,unique = true)
    private int meterNumber;

    private Long masterTechnicianId;

    @OneToMany(mappedBy = "installation", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MeterReading> readings;

    @OneToOne
    @JoinColumn(name = "connection_request_id")
    private ConnectionRequest connectionRequest;

    private LocalDate installationDate;
}
