package com.example.demo_Electricity_App.Entity.Master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    private boolean is_Active;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<District> districts;

    @ManyToOne
    @JoinColumn(name = "state_head_id")
    private Users stateHead;
}
