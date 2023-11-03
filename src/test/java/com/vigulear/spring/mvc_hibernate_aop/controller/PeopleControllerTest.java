package com.vigulear.spring.mvc_hibernate_aop.controller;

import com.vigulear.spring.mvc_hibernate_aop.config.SpringWebInitializer;
import com.vigulear.spring.mvc_hibernate_aop.entity.Person;
import com.vigulear.spring.mvc_hibernate_aop.entity.Skill;
import com.vigulear.spring.mvc_hibernate_aop.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Constantin Vigulear
 */
@SpringJUnitConfig(classes = SpringWebInitializer.class)
//@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
class PeopleControllerTest {

  @Mock private PersonServiceImpl personService;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(new PeopleController(personService)).build();
  }

  @Test
  public void test_personController_showAllPersons() throws Exception {
    List<Person> people = new ArrayList<>();

    when(personService.findAll()).thenReturn(people);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/people"))
        .andExpect(status().isOk()) // Check the HTTP status
        .andExpect(view().name("people/all-people"))
        .andExpect(model().attribute("allPeople", people)) // Check the expected JSP view
        .andExpect(content().string(containsString(""))); // Check the expected JSP view
  }

  @Test
  void test_personController_showPeopleBySkill() throws Exception {
    List<Person> peopleBySkill = new ArrayList<>();

    when(personService.showPeopleBySkillId(1L)).thenReturn(peopleBySkill);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/people/peopleBySkill").param("skillId", "1"))
        .andExpect(status().isOk())
        .andExpect(view().name("people/all-people"))
        .andExpect(model().attribute("allPeople", peopleBySkill))
        .andExpect(content().string(containsString("")));
  }

  @Test
  void test_personController_getPersonSkills() throws Exception {
    Person person = new Person();
    List<Skill> personSkills = new ArrayList<>();

    when(personService.findById(1L)).thenReturn(person);
    when(personService.findSkillsByPersonId(1L)).thenReturn(personSkills);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/people/personSkills").param("personId", "1"))
        .andExpect(status().isOk())
        .andExpect(view().name("people/person-skills"))
        .andExpect(model().attribute("person", person))
        .andExpect(model().attribute("skills", personSkills))
        .andExpect(content().string(containsString("")));
  }

  @Test
  void test_personController_addNewPerson() throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.get("/people/addNewPerson"))
        .andExpect(status().isOk())
        .andExpect(view().name("people/person-info"))
        .andExpect(model().attribute("person", any(Person.class)))
        .andExpect(content().string(containsString("")));
  }

  @Test
  void test_personController_savePerson() throws Exception {
    Person person = new Person();

    when(personService.persist(person)).thenReturn(person);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/people/savePerson").flashAttr("person", person))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/people")) // Check for a redirect
        .andExpect(content().string(containsString("")));

    person.setId(1L);
    mockMvc
        .perform(MockMvcRequestBuilders.get("/people/savePerson").flashAttr("person", person))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/people")) // Check for a redirect
        .andExpect(content().string(containsString("")));
  }

  @Test
  void test_personController_updatePerson() throws Exception {
    Person person = new Person();

    when(personService.findById(1L)).thenReturn(person);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/people/updatePerson").param("personId", "1"))
        .andExpect(status().isOk())
        .andExpect(view().name("people/person-info"))
        .andExpect(model().attribute("person", person))
        .andExpect(content().string(containsString("")));
  }

  @Test
  void test_personController_deletePerson() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/people/deletePerson").param("personId", "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/people")) // Check for a redirect
        .andExpect(content().string(containsString("")));
  }

  @Test
  void test_personController_addSkillToPerson() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/people/addSkillToPerson")
                .param("skillId", "1")
                .param("skillCost", "100")
                .param("personId", "1"))
        .andExpect(status().isFound())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/people/personSkills?personId=1")) // Check for a redirect
        .andExpect(content().string(containsString("")));
  }

  @Test
  void test_personController_deleteSkillFromPerson() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/people/deleteSkillFromPerson")
                .param("skillId", "1")
                .param("skillCost", "100")
                .param("personId", "1"))
        .andExpect(status().isFound())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/people/personSkills?personId=1")) // Check for a redirect
        .andExpect(content().string(containsString("")));
  }
}
