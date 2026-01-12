package com.jb98.springsecurity.demo_spring_security.configuration;

import com.jb98.springsecurity.demo_spring_security.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        // Scheduled jobs, batch processes, async tasks
        if (authentication == null) {
            return Optional.of("SYSTEM");
        }
        // Unauthenticated user
        if (!authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.of("ANONYMOUS");
        }
        var principal = (User) authentication.getPrincipal();
        return Optional.ofNullable(principal.getUsername());
    }
}
