package com.example.demo_Electricity_App.Service.Master;

import com.example.demo_Electricity_App.DTO.Master.Response.UsersResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Users;
import com.example.demo_Electricity_App.Enums.Role;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Master.UsersMapper;
import com.example.demo_Electricity_App.Repository.Master.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository repository;
    private final UsersMapper mapper;

    @Autowired
    private UsersRepository usersRepository;

    public List<UsersResponseDTO> getUsers() {
        return repository.findAll().stream()
                .map(mapper::toUsersResponseDTO).collect(Collectors.toList());
    }

    public UsersResponseDTO getUserById(Long id) {
        return repository.findById(id).map(mapper::toUsersResponseDTO).orElseThrow(() -> new ResourceNotFoundException("Users Not Found"));
    }

    public List<UsersResponseDTO> getUsersByRole(Role role) {
        return usersRepository.findAll().stream()
                .filter(u -> u.getRole() == role)
                .map(mapper::toUsersResponseDTO).collect(Collectors.toList());
    }

    public String deactivateUser(Long id) {
        Users user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        user.set_Active(false);
        repository.save(user);
        return "User Deactivated";
    }
}
