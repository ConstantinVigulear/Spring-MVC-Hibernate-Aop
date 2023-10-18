package com.vigulear.spring.mvc_hibernate_aop.controller;

import com.vigulear.spring.mvc_hibernate_aop.entity.Person;
import com.vigulear.spring.mvc_hibernate_aop.entity.Skill;
import com.vigulear.spring.mvc_hibernate_aop.service.PersonService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"people", "people/"})
public class PeopleController {

  private final PersonService personService;

  public PeopleController(PersonService personService) {
    this.personService = personService;
  }

  @RequestMapping()
  public String showAllPersons(Model model) {

    List<Person> allPeople = personService.findAll();
    model.addAttribute("allPeople", allPeople);

    return "people/all-people";
  }

  @RequestMapping("/peopleBySkill")
  public String showPeopleBySkill(@RequestParam("skillId") String skillId, Model model) {

    List<Person> peopleBySkill = personService.showPeopleBySkillId(Long.parseLong(skillId));

    model.addAttribute("allPeople", peopleBySkill);

    return "people/all-people";
  }

  @RequestMapping("/personSkills")
  public String getPersonSkills(@RequestParam("personId") String id, Model model) {

    Person person = personService.findById(Long.parseLong(id));
    List<Skill> skills = personService.findSkillsByPersonId(Long.parseLong(id));

    model.addAttribute("person", person);
    model.addAttribute("skills", skills);

    return "people/person-skills";
  }

  @RequestMapping("/addNewPerson")
  public String addNewPerson(Model model) {
    Person person = new Person();

    model.addAttribute("person", person);
    return "people/person-info";
  }

  @RequestMapping("/savePerson")
  public String savePerson(@ModelAttribute("person") Person person) {

    if (person.getId() == null) {
      personService.persist(person);
    } else {
      personService.update(person);
    }

    return "redirect:/people";
  }

  @RequestMapping("/updatePerson")
  public String updatePerson(@RequestParam("personId") String id, Model model) {

    Person person = personService.findById(Long.parseLong(id));

    model.addAttribute("person", person);

    return "people/person-info";
  }

  @RequestMapping("/deletePerson")
  public String deletePerson(@RequestParam("personId") Long id) {
    personService.deleteById(id);

    return "redirect:/people";
  }

  @RequestMapping("/addSkillToPerson")
  public String addSkillToPerson(
      @RequestParam("skillId") String skillId,
      @RequestParam("skillCost") String skillCost,
      @RequestParam("personId") String personId) {

    personService.addSkillToPerson(
        Long.parseLong(skillId), Long.parseLong(personId), Integer.parseInt(skillCost));

    return "redirect:/people/personSkills?personId=" + personId;
  }

  @RequestMapping("/deleteSkillFromPerson")
  public String deleteSkillFromPerson(
      @RequestParam("skillId") String skillId,
      @RequestParam("skillCost") String skillCost,
      @RequestParam("personId") String personId) {

    personService.removeSkillFromPerson(
        Long.parseLong(skillId), Long.parseLong(personId), Integer.parseInt(skillCost));

    return "redirect:/people/personSkills?personId=" + personId;
  }
}
