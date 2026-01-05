package com.jb98.springsecurity.demo_spring_security.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.jb98.springsecurity.demo_spring_security.exception.ErrorCode.ACCESS_DENIED;
import static com.jb98.springsecurity.demo_spring_security.exception.ErrorCode.BAD_CREDENTIALS;
import static com.jb98.springsecurity.demo_spring_security.exception.ErrorCode.BUSINESS_RULE_VIOLATION;
import static com.jb98.springsecurity.demo_spring_security.exception.ErrorCode.DUPLICATE_KEY;
import static com.jb98.springsecurity.demo_spring_security.exception.ErrorCode.ENTITY_NOT_FOUND;
import static com.jb98.springsecurity.demo_spring_security.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.jb98.springsecurity.demo_spring_security.exception.ErrorCode.METHOD_ARGUMENT_NOT_VALID;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var fields = new HashMap<String, List<String>>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String message = error.getDefaultMessage();
            fields.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(message);
        });
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .title(METHOD_ARGUMENT_NOT_VALID.getTitle())
                        .error(METHOD_ARGUMENT_NOT_VALID.name())
                        .validationErrors(fields)
                        .build(),
                METHOD_ARGUMENT_NOT_VALID.getStatus()
        );
    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .title(BUSINESS_RULE_VIOLATION.getTitle())
                        .error(BUSINESS_RULE_VIOLATION.name())
                        .detail(ex.getLocalizedMessage())
                        .build(),
                BUSINESS_RULE_VIOLATION.getStatus()
        );
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException ex) {
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .title(DUPLICATE_KEY.getTitle())
                        .error(DUPLICATE_KEY.name())
                        .detail(ex.getLocalizedMessage())
                        .build(),
                DUPLICATE_KEY.getStatus()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .title(ENTITY_NOT_FOUND.getTitle())
                        .error(ENTITY_NOT_FOUND.name())
                        .detail(ex.getLocalizedMessage())
                        .build(),
                ENTITY_NOT_FOUND.getStatus()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .title(BAD_CREDENTIALS.getTitle())
                        .error(BAD_CREDENTIALS.name())
                        .detail(BAD_CREDENTIALS.getDefaultMessage())
                        .build(),
                BAD_CREDENTIALS.getStatus()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .title(ACCESS_DENIED.getTitle())
                        .error(ACCESS_DENIED.name())
                        .detail(ex.getLocalizedMessage())
                        .build(),
                ACCESS_DENIED.getStatus()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .title(INTERNAL_SERVER_ERROR.getTitle())
                        .error(INTERNAL_SERVER_ERROR.name())
                        .detail(INTERNAL_SERVER_ERROR.getDefaultMessage())
                        .build(),
                INTERNAL_SERVER_ERROR.getStatus()
        );
    }
}
