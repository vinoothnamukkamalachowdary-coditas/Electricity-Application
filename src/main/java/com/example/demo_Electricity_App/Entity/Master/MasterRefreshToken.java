package com.example.demo_Electricity_App.Entity.Master;

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
public class MasterRefreshToken {
    @Id
    private String refreshToken;

    @ManyToOne
    private Users user;

    private LocalDateTime expireTime;
}
