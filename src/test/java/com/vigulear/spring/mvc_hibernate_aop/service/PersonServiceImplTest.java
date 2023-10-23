package com.vigulear.spring.mvc_hibernate_aop.service;

import com.vigulear.spring.mvc_hibernate_aop.dao.PersonDao;
import com.vigulear.spring.mvc_hibernate_aop.entity.Person;

import com.vigulear.spring.mvc_hibernate_aop.entity.Skill;
import com.vigulear.spring.mvc_hibernate_aop.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Constantin Vigulear
 */
@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

  @Mock private PersonDao personDao;

  @InjectMocks private PersonServiceImpl personService;

  private Person person;

  @BeforeEach
  public void setUp() {
    person =
        new Person()
            .setId(1L)
            .setName("First")
            .setSurname("First")
            .setEmail("first.first@gmail.com");
  }

  @Test
  public void test_personService_persist_returnPerson() {
    when(personDao.persist(any(Person.class))).thenReturn(person);

    Person persistedPerson = personService.persist(person);

    verify(personDao, times(1)).persist(any(Person.class));
    assertNotNull(persistedPerson);
  }

  @Test
  public void test_personService_update_returnUpdatedPerson() {
    when(personDao.update(person)).thenReturn(person);

    Person updatedPerson = personService.update(person);

    verify(personDao, times(1)).update(any(Person.class));
    assertNotNull(updatedPerson);
    assertEquals("First", updatedPerson.getName());
  }

  @Test
  public void test_personService_findById_returnPerson() {
    person.setId(1L);

    when(personDao.findById(1L)).thenReturn(person);

    Person foundPersonById = personService.findById(1L);

    verify(personDao, times(1)).findById(1L);
    assertNotNull(foundPersonById);
    assertEquals(1L, foundPersonById.getId());
  }

  @Test
  public void personService_findAll_returnPersonList() {
    List<Person> people =
        new ArrayList<>() {
          {
            add(new Person());
            add(new Person());
            add(new Person());
          }
        };

    when(personDao.findAll()).thenReturn(people);

    List<Person> foundPeople = personService.findAll();

    verify(personDao, times(1)).findAll();
    assertNotNull(foundPeople);
    assertEquals(people.size(), foundPeople.size());
  }

  @Test
  public void test_personService_delete() {
    personService.delete(person);
    verify(personDao).delete(person);
  }

  @Test
  public void test_personService_deleteById() {
    personService.deleteById(any(Long.class));
    verify(personDao).deleteById(any(Long.class));
  }

  @Test
  public void test_personService_findSkillsByPersonId_returnSkillList() {
    List<Skill> skills =
        new ArrayList<>() {
          {
            add(new Skill());
            add(new Skill());
            add(new Skill());
          }
        };

    when(personDao.findSkillsByPersonId(any(Long.class))).thenReturn(skills);

    List<Skill> foundSkills = personService.findSkillsByPersonId(1L);

    verify(personDao, times(1)).findSkillsByPersonId(1L);
    assertNotNull(foundSkills);
    assertEquals(skills, foundSkills);
  }

  @Test
  public void test_personService_rddSkillToPerson() {
    personService.addSkillToPerson(1L, 1L, 1);
    verify(personDao).addSkillToPerson(1L, 1L, 1);
  }

  @Test
  public void test_personService_removeSkillToPerson() {
    personService.removeSkillFromPerson(1L, 1L, 1);
    verify(personDao).removeSkillFromPerson(1L, 1L, 1);
  }

  @Test
  public void test_personService_showPeopleBySkill_returnPersonList() {
    List<Person> people =
            new ArrayList<>() {
              {
                add(new Person());
                add(new Person());
                add(new Person());
              }
            };

    when(personDao.showPeopleBySkillId(any(Long.class))).thenReturn(people);

    List<Person> foundPeople = personService.showPeopleBySkillId(any(Long.class));

    verify(personDao, times(1)).showPeopleBySkillId(any(Long.class));
    assertNotNull(foundPeople);
    assertEquals(people.size(), foundPeople.size());
  }
}
