package com.example.demo_Electricity_App.Mapper.Master;

import com.example.demo_Electricity_App.DTO.Master.Response.UsersResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Users;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {
    public UsersResponseDTO toUsersResponseDTO(Users user){
        UsersResponseDTO usersDTO = new UsersResponseDTO();
        usersDTO.setId(user.getId());
        usersDTO.setName(user.getName());
        usersDTO.setEmail(user.getEmail());
        usersDTO.setRole(user.getRole());
        usersDTO.setActive(user.is_Active());
        return usersDTO;
    }
}
