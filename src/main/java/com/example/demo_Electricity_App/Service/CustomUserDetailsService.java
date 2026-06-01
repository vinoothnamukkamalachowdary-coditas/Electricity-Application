package com.example.demo_Electricity_App.Service;

import com.example.demo_Electricity_App.Repository.Master.UsersRepository;
import com.example.demo_Electricity_App.Repository.Tenant.TenantUsersRepository;
import com.example.demo_Electricity_App.Tenant.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TenantUsersRepository tenantUsersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(TenantContext.getTenant() == null) return usersRepository.findByEmail(username);
        else if(TenantContext.getTenant().equals("public")) return usersRepository.findByEmail(username);
        else return tenantUsersRepository.findByEmail(username);
    }
}
