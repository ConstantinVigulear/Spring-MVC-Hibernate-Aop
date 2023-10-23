package com.vigulear.spring.mvc_hibernate_aop.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author Constantin Vigulear
 */
@Aspect
@Component
public class TransactionAspect {

  private final PlatformTransactionManager transactionManager;
  private final Logger logger = LogManager.getLogger(LoggingAspect.class);

  public TransactionAspect(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  @Around("@annotation(jakarta.transaction.Transactional)")
  public Object aroundTransaction(ProceedingJoinPoint proceedingJoinPoint) throws Exception {

    logger.info("Starting a transaction.");

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName(proceedingJoinPoint.getSignature().getName());
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    TransactionStatus status = transactionManager.getTransaction(def);

    try {
      Object result = proceedingJoinPoint.proceed();
      transactionManager.commit(status);
      logger.info("Transaction is completed successfully.");
      return result;
    } catch (Exception e) {
      transactionManager.rollback(status);
      logger.info("Transaction did not complete.");
      throw e;
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }
}
