package com.example.demo_Electricity_App.Controller.Master;

import com.example.demo_Electricity_App.DTO.Master.Response.UsersResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Users;
import com.example.demo_Electricity_App.Enums.Role;
import com.example.demo_Electricity_App.Service.Master.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGEMENT_TEAM')")
    @GetMapping("/getAll")
    public ResponseEntity<List<UsersResponseDTO>> getUsers() {
        return ResponseEntity.ok(usersService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.getUserById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGEMENT_TEAM')")
    @GetMapping("/by-role")
    public ResponseEntity<List<UsersResponseDTO>> getUsersByRole(
            @RequestParam Role role) {
        return ResponseEntity.ok(usersService.getUsersByRole(role));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGEMENT_TEAM')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        usersService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }

}
