package com.jb98.springsecurity.demo_spring_security.controller.request;

import jakarta.validation.constraints.Pattern;

public record RoleRequest (
        @Pattern(
                regexp = "^ROLE_[A-Z0-9]+(_[A-Z0-9]+)*$",
                message = "Role name must start with 'ROLE_' and contain only uppercase letters and underscores"
        )
        String name
) {
}
