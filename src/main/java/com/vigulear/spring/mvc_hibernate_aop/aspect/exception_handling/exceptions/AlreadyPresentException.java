package com.vigulear.spring.mvc_hibernate_aop.aspect.exception_handling.exceptions;

/**
 * @author Constantin Vigulear
 */
public class AlreadyPresentException extends RuntimeException {
  public AlreadyPresentException(String message) {
    super(message);
  }
}
