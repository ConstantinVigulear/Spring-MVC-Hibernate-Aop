package com.vigulear.spring.mvc_hibernate_aop.aspect.exception_handling;

import com.vigulear.spring.mvc_hibernate_aop.aspect.exception_handling.exceptions.AlreadyPresentException;
import com.vigulear.spring.mvc_hibernate_aop.aspect.exception_handling.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Constantin Vigulear
 */
class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

  @BeforeEach
  void setUp() {}

  @Test
  void handleNotFoundException() {
    String exceptionMessage = "NotFoundException";
    NotFoundException exception = new NotFoundException(exceptionMessage);

    ModelAndView modelAndView = globalExceptionHandler.handleNotFoundException(exception);

    // Assert the response status code and body
    assertEquals(HttpStatus.NOT_FOUND, modelAndView.getStatus());
    assertEquals("error/error", modelAndView.getViewName());
    assertEquals(exceptionMessage, modelAndView.getModel().get("exception"));
  }

  @Test
  void handleAlreadyPresentException() {
    AlreadyPresentException exception = new AlreadyPresentException("Test Exception");

    ModelAndView modelAndView = globalExceptionHandler.handleAlreadyPresentException(exception);

    // Assert the response status code and body
    assertEquals(HttpStatus.BAD_REQUEST, modelAndView.getStatus());
    assertEquals("error/error", modelAndView.getViewName());
    assertEquals("Test Exception", modelAndView.getModel().get("exception"));
  }

  @Test
  void handleException() {
    Exception exception = new Exception("Test Exception");

    ModelAndView modelAndView = globalExceptionHandler.handleException(exception);

    // Assert the response status code and body
    assertEquals(HttpStatus.BAD_REQUEST, modelAndView.getStatus());
    assertEquals("error/error", modelAndView.getViewName());
    assertEquals("Test Exception", modelAndView.getModel().get("exception"));
  }
}
