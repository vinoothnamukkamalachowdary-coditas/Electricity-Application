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
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String districtName;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<City> cities;

    @ManyToOne
    @JoinColumn(name = "district_head_id")
    private Users districtHead;
}
