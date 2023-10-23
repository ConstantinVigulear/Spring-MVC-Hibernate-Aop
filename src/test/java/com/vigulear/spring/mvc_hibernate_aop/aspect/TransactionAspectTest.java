package com.vigulear.spring.mvc_hibernate_aop.aspect;

import com.vigulear.spring.mvc_hibernate_aop.entity.Person;
import com.vigulear.spring.mvc_hibernate_aop.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Constantin Vigulear
 */

@ExtendWith(MockitoExtension.class)
class TransactionAspectTest {

  @Mock
  private final Logger logger = LogManager.getLogger(LoggingAspect.class);

  @Mock private PersonService personService;

  @Test
  void aroundTransaction() {
    Person person = new Person();

    when(personService.persist(any(Person.class))).thenReturn(person);

    Person persistedPerson = personService.persist(person);

      verify(logger, times(2)).info(anyString());

  }
}
