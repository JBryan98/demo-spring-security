package com.jb98.springsecurity.demo_spring_security.security;

import com.jb98.springsecurity.demo_spring_security.configuration.ApplicationAuditAware;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeansConfig {
    private final UserDetailsService userDetailsService;

    /**
     * This bean is responsible for handling authentication.
     * It uses a DaoAuthenticationProvider to authenticate users
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        // Authentication provider that retrieves user details from a data source
        var authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        // ProviderManager delegates authentication to the configured provider
        return new ProviderManager(authProvider);
    }

    /**
     * This bean is used to hash and verify passwords.
     * BCrypt is the recommended algorithm in Spring Security
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * This bean is used by Spring Data JPA Auditing
     * It provides the current authenticated user for @CreatedBy and @LastModifiedBy annotations
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return new ApplicationAuditAware();
    }
}
