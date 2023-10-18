package com.vigulear.spring.mvc_hibernate_aop.aspect.exception_handling.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
