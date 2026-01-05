package com.jb98.springsecurity.demo_spring_security.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Standard error response based on RFC 7807 (Problem Details for HTTP APIs).
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    /**
     * Short, human-readable summary of the problem
     * Intended for end users and UI display
     * Example: "Authentication failed"
     */
    private String title;

    /**
     * Application-specific error code.
     * Example: "BAD_CREDENTIALS", "DUPLICATE_KEY"
     */
    private String error;

    /**
     * Human-readable explanation of the error
     * This field should NOT expose sensitive or internal details
     * Example: "Invalid username or password"
     */
    private String detail;

    /**
     * Validation errors grouped by field name.
     * Used mainly for 400 Bad Requests responses when input validation fails.
     * Example:
     * {
     *   "email": ["must not be blank", "must be a valid email address"],
     *   "password": ["must be at least 8 characters long"]
     * }
     */
    private Map<String, List<String>> validationErrors;
}
