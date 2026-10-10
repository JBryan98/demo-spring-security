package com.jb98.springsecurity.demo_spring_security.auth;

import java.util.List;

public record AuthenticationResponse(
        String token,
        Profile profile
) {
    protected record Profile(
            String email,
            String firstName,
            String lastName,
            List<String> roles
    ) {
    }
}
