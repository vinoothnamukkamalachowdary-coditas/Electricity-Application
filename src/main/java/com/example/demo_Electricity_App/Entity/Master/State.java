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
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean is_Active;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<District> districts;

    @ManyToOne
    @JoinColumn(name = "state_head_id")
    private Users stateHead;
}
