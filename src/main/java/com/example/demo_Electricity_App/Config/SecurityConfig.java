package com.example.demo_Electricity_App.Config;
import com.example.demo_Electricity_App.Filter.JwtFilter;
import com.example.demo_Electricity_App.Service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth


                        .requestMatchers(
                                "/api/auth/master/login",
                                "/api/auth/master/register",
                                "/api/auth/tenant/login",
                                "/api/send/invitation/accept",
                                "/api/tenant/invitation/accept",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/webjars/**"
                        ).permitAll()


                        .requestMatchers(
                                "/api/master/users/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGEMENT_TEAM"
                        )

                        .requestMatchers(
                                "/api/master/portfolios/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGEMENT_TEAM",
                                "SALES_TEAM"
                        )

                        .requestMatchers(
                                "/api/master/states/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGEMENT_TEAM",
                                "STATE_HEAD"
                        )

                        .requestMatchers(
                                "/api/master/distircts/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGEMENT_TEAM",
                                "STATE_HEAD",
                                "DISTRICT_HEAD"
                        )

                        .requestMatchers(
                                "/api/master/city/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGEMENT_TEAM",
                                "STATE_HEAD",
                                "DISTRICT_HEAD",
                                "CITY_HEAD"
                        )

                        .requestMatchers(
                                "/api/master/area/**",
                                "/api/master/servicearea/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGEMENT_TEAM",
                                "STATE_HEAD",
                                "DISTRICT_HEAD",
                                "CITY_HEAD"
                        )


                        .requestMatchers(
                                "/api/tenant/meter-types/**"
                        ).hasAnyRole(
                                "TENANT_ADMIN",
                                "POC",
                                "OPERATIONAL_HEAD"
                        )

                        .requestMatchers(
                                "/api/tenant/bpo/**",
                                "/api/tenant/bpo-states/**"
                        ).hasAnyRole(
                                "TENANT_ADMIN",
                                "OPERATIONAL_HEAD"
                        )

                        .requestMatchers(
                                "/api/tenant/customers/**",
                                "/api/tenant/connection-request/**"
                        ).hasAnyRole(
                                "CRM",
                                "OPERATIONAL_HEAD",
                                "TENANT_ADMIN"
                        )

                        .requestMatchers(
                                "/api/tenant/meter-installation/**"
                        ).hasAnyRole(
                                "TECHNICIAN",
                                "CITY_HEAD"
                        )

                        .requestMatchers(
                                "/api/tenant/meter-reading/**"
                        ).hasAnyRole(
                                "BILLER",
                                "CITY_HEAD"
                        )

                        .requestMatchers(
                                "/api/tenant/bills/**"
                        ).hasAnyRole(
                                "BILLER",
                                "CITY_HEAD",
                                "OPERATIONAL_HEAD",
                                "TENANT_ADMIN"
                        )

                        .requestMatchers(
                                "/api/tenant/complaints/**"
                        ).hasAnyRole(
                                "PERSONNEL",
                                "MANAGER",
                                "HIGHER_MANAGER",
                                "TECHNICIAN"
                        )

                        .anyRequest()
                        .authenticated()
                )

                .httpBasic(Customizer.withDefaults())

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(customUserDetailsService);

        provider.setPasswordEncoder(
                passwordEncoder()
        );

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
