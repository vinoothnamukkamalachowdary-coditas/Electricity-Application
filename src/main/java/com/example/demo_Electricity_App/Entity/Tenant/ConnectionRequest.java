package com.example.demo_Electricity_App.Entity.Tenant;

import com.example.demo_Electricity_App.Enums.ConnectionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConnectionStatus status;

    private LocalDateTime requestedAt;

    private LocalDateTime approvedAt;

    @ManyToOne
    @JoinColumn(name = "customers_id")
    private Customers customer;

    @ManyToOne
    @JoinColumn(name = "meter_type_id")
    private MeterTypes meterType;

    // who approved (Operational Head or CRM with approval authority)
    @ManyToOne
    @JoinColumn(name = "approved_by_id")
    private TenantUsers approvedBy;

    // Cross-schema reference to Master.ServiceArea.id (determines the technician)
    private Long masterServiceAreaId;
}
