package com.jb98.springsecurity.demo_spring_security.dto;

import java.util.List;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String username,
        String fullName,
        boolean admin,
        List<RoleDto> roles
) {
}
