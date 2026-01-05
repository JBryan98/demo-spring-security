package com.jb98.springsecurity.demo_spring_security.security;

import com.jb98.springsecurity.demo_spring_security.repository.UserRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of UserDetailsService
 * Spring Security uses this service to load the user information during the authentication process.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    /**
     * This method is called by Spring Security during authentication
     * @param username the username identifying the user whose data is required.
     * @return UserDetails containing user information and authorities
     * @throws UsernameNotFoundException if the user does not exist
     */
    @Override
    public UserDetails loadUserByUsername(@Nonnull String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername username {}", username);
        return repository.
                findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username '" + username + "' not found."));
    }
}
