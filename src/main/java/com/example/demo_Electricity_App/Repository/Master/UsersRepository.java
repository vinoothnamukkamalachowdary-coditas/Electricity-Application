package com.example.demo_Electricity_App.Repository.Master;

import com.example.demo_Electricity_App.Entity.Master.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String username);
    boolean existsByEmail(String email);
}
