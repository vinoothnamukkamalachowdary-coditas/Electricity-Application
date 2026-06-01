package com.example.demo_Electricity_App.Entity.Tenant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantRefreshToken {
    @Id
    private String token;

    @ManyToOne
    private TenantUsers users;

    private LocalDateTime expireTime;
}
