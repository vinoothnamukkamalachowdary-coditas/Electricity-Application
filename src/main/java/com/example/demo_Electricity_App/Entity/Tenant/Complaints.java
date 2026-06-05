package com.example.demo_Electricity_App.Entity.Tenant;

import com.example.demo_Electricity_App.Enums.ComplaintsStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Complaints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customers_id")
    private Customers bpoState;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String complaintDescription;

    @Enumerated(EnumType.STRING)
    private ComplaintsStatus status;

    @ManyToOne
    @JoinColumn(name = "assigned_personnel_id")
    private TenantUsers assignedPersonnel;

    private Long masterTechnicianId;

    private Integer Escalation_level;

    private LocalDateTime raisedAt;
    private LocalDateTime resolvedAt;
    private LocalDateTime lastUpdatedAt;
}
