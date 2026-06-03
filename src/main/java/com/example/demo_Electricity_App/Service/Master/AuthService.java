package com.example.demo_Electricity_App.Service.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.LoginRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Request.RegisterRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.LoginResponseDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.RegisterResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Users;
import com.example.demo_Electricity_App.Exception.UserAlreadyExistsException;
import com.example.demo_Electricity_App.Repository.Master.UsersRepository;
import com.example.demo_Electricity_App.Util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public RegisterResponseDTO register(RegisterRequestDTO dto) {
        if(usersRepository.existsByEmail(dto.getEmail())){
            throw new UserAlreadyExistsException("User Already Present");
        }
        Users users = new Users();
        users.setName(dto.getName());
        users.setEmail(dto.getEmail());
        users.setRole(dto.getRole());
        users.set_Active(true);
        users.setCreated_at(LocalDateTime.now());
        users.setPassword(passwordEncoder.encode(dto.getPassword()));
        usersRepository.save(users);

        RegisterResponseDTO responseDTO = new RegisterResponseDTO();
        responseDTO.setName(users.getName());
        responseDTO.setEmail(users.getEmail());
        return responseDTO;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        Users users = usersRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("User Not Found"));
        if(!passwordEncoder.matches(dto.getPassword(),users.getPassword())){
            throw new RuntimeException("Wrong Password");
        }
        String token = jwtUtil.generateMasterToken(users.getEmail(), users.getRole().name());
        return LoginResponseDTO.builder()
                .accessToken(token)
                .email(users.getEmail())
                .role(users.getRole())
                .build();
    }
}
