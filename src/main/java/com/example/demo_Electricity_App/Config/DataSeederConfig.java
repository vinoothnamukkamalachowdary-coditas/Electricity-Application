package com.example.demo_Electricity_App.Config;

import com.example.demo_Electricity_App.Entity.Master.Users;
import com.example.demo_Electricity_App.Repository.Master.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.example.demo_Electricity_App.Enums.Role.ADMIN;

@Component
@RequiredArgsConstructor
public class DataSeederConfig implements CommandLineRunner {
    private final UsersRepository usersRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        if(!usersRepo.existsByEmail("vinoothnamukkamala@gmail.com")){
            Users platformadmin =  Users.builder()
                    .name("Vinoothna")
                    .email("vinoothnamukkamala@gmail.com")
                    .password(passwordEncoder.encode("vinoo@3110"))
                    .role(ADMIN)
                    .is_Active(true)
                    .created_at(LocalDateTime.now())
                    .build();

            usersRepo.save(platformadmin);
        }
    }
}