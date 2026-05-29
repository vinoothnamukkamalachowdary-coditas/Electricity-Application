package com.example.demo_Electricity_App.Entity.Master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private String Role;

    private boolean is_Active;

    private LocalDateTime created_at;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Portfolios> portfolios;
}
