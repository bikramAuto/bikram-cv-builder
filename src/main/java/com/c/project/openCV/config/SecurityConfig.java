package com.c.project.openCV.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF and allow access to registration and login endpoints
        http.csrf().disable()
            .authorizeRequests()
            .requestMatchers("/api/users/register", "/api/users/login").permitAll() // Allow unauthenticated access
            .anyRequest().authenticated(); // Secure other endpoints
        return http.build();
    }
}
