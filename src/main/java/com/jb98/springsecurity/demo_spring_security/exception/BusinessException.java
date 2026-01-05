package com.jb98.springsecurity.demo_spring_security.exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
