package com.example.demo_Electricity_App.Entity.Tenant;

import com.example.demo_Electricity_App.Enums.Tenant.ComplaintsStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Customers customer;

    private String title;

    private String complaintDescription;

    @Enumerated(EnumType.STRING)
    private ComplaintsStatus status;

    private Long assigned_Personnel_Id;

    private Long technician_Id;

    private Integer Escalation_level;
}
