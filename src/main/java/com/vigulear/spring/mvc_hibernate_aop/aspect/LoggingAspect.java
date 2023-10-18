package com.vigulear.spring.mvc_hibernate_aop.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author Constantin Vigulear
 */
@Component
@Aspect
public class LoggingAspect {

  private final Logger logger = LogManager.getLogger(LoggingAspect.class);

  @Pointcut("execution(* com.vigulear.spring.mvc_hibernate_aop.dao.*.*(..))")
  public void allDaoMethods() {}

  @Pointcut("execution(* com.vigulear.spring.mvc_hibernate_aop.service.*.*(..))")
  public void allServiceMethods() {}

  @Pointcut(
      "execution(String com.vigulear.spring.mvc_hibernate_aop.controller.PeopleController.showAllPersons(..))")
  public void showAllPeople() {}

  @Pointcut(
          "execution(String com.vigulear.spring.mvc_hibernate_aop.controller.SkillController.showAllSkills(..))")
  public void showAllSkills() {}


  @Around("allDaoMethods()")
  public Object aroundAllRepositoriesLoggingAdvice(ProceedingJoinPoint proceedingJoinPoint)
      throws Throwable {
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

    String methodName = methodSignature.getName();

    logger.info("Begin of " + methodName);

    Object targetMethodResult = proceedingJoinPoint.proceed();

    logger.info("End of " + methodName);

    return targetMethodResult;
  }

  @AfterThrowing(pointcut = "allDaoMethods()", throwing = "exception")
  public void afterThrowingAllRepositoriesExceptionLoggingAdvice(Throwable exception) {
    logger.warn("Exception caught: " + exception.getMessage());
  }

  @After("allServiceMethods()")
  public void afterAllServicesLoggingAdvice(JoinPoint joinPoint) {

    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    String methodName = methodSignature.getName();

    logger.info("afterAllServicesAdvice: Service terminated - " + methodName);
  }

  @Before("showAllPeople())")
  public void beforeShowAllPersonsLoggingAdvice() {
    logger.info("beforeShowAllPersonsLoggingAdvice: Controller performs request to get all people");
  }

  @AfterReturning(pointcut = "showAllSkills()", returning = "page")
  public void afterReturningShowAllSkillsLoggingAdvice(String page) {
    logger.info("afterReturningShowAllSkillsLoggingAdvice: page successfully rendered - " + page);
  }
}