package com.jb98.springsecurity.demo_spring_security.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UpdateUserRequest(
        @NotBlank(message = "First name is mandatory")
        String firstName,

        @NotBlank(message = "Last name is mandatory")
        String lastName,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email is incorrect")
        String email,

        @NotEmpty(message = "You should provide at least one role id in the array")
        Set<Long> roleIds
) {
}
