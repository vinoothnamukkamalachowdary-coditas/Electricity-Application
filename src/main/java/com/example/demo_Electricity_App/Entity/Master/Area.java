package com.example.demo_Electricity_App.Entity.Master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private boolean is_Active;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<ServiceArea> services;

}
