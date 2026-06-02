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
    private static final String MASTER_SCHEMA = "public";

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TenantUsersRepository tenantUsersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String currentTenant = TenantContext.getTenant();

        if (currentTenant == null || MASTER_SCHEMA.equals(currentTenant)) {
            UserDetails user = usersRepository.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException(
                        "Master user not found with email: " + username);
            }
            return user;
        }

        UserDetails tenantUser = tenantUsersRepository.findByEmail(username);
        if (tenantUser == null) {
            throw new UsernameNotFoundException(
                    "Tenant user not found with email: " + username
                            + " in schema: " + currentTenant);
        }
        return tenantUser;
    }
}
