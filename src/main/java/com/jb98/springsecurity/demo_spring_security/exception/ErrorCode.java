package com.jb98.springsecurity.demo_spring_security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    METHOD_ARGUMENT_NOT_VALID("The request contains invalid values", HttpStatus.BAD_REQUEST, null),
    DUPLICATE_KEY("The resource already exists", HttpStatus.CONFLICT, null),
    ENTITY_NOT_FOUND("The resource was not found", HttpStatus.NOT_FOUND, null),
    ACCESS_DENIED("Access denied", HttpStatus.FORBIDDEN, null),
    BUSINESS_RULE_VIOLATION("Business error", HttpStatus.CONFLICT, null),
    INTERNAL_SERVER_ERROR("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred."),
    BAD_CREDENTIALS("Authentication Failed", HttpStatus.UNAUTHORIZED, "Incorrect username or password."),
    USER_NOT_FOUND("Authentication Failed", HttpStatus.UNAUTHORIZED, "The provided token is invalid."),
    EXPIRED_JWT("Authentication Failed", HttpStatus.UNAUTHORIZED, "The provided token has expired."),
    INVALID_JWT("Authentication Failed", HttpStatus.UNAUTHORIZED, "The provided token is invalid.");

    private final String title;
    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(String title, HttpStatus status, String defaultMessage) {
        this.title = title;
        this.status = status;
        this.defaultMessage = defaultMessage;
    }
}
