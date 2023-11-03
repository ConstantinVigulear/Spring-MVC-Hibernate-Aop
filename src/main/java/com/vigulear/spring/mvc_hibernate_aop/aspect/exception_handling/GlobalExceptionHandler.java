package com.vigulear.spring.mvc_hibernate_aop.aspect.exception_handling;

import com.vigulear.spring.mvc_hibernate_aop.aspect.exception_handling.exceptions.AlreadyPresentException;
import com.vigulear.spring.mvc_hibernate_aop.aspect.exception_handling.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ModelAndView handleNotFoundException(NotFoundException exception) {
    ModelAndView model = new ModelAndView("error/error");
    model.addObject("exception", exception.getMessage());
    model.setStatus(HttpStatus.NOT_FOUND);

    return model;
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ModelAndView handleAlreadyPresentException(AlreadyPresentException exception) {
    ModelAndView model = new ModelAndView("error/error");
    model.addObject("exception", exception.getMessage());
    model.setStatus(HttpStatus.BAD_REQUEST);

    return model;
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ModelAndView handleException(Exception exception) {
    ModelAndView model = new ModelAndView("error/error");
    model.addObject("exception", exception.getMessage());
    model.setStatus(HttpStatus.BAD_REQUEST);

    return model;
  }
}
