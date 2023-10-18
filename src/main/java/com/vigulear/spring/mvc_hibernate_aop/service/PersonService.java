package com.vigulear.spring.mvc_hibernate_aop.service;

import com.vigulear.spring.mvc_hibernate_aop.entity.Person;
import com.vigulear.spring.mvc_hibernate_aop.entity.Skill;

import java.util.List;

public interface PersonService extends GenericService<Person> {
    List<Skill> findSkillsByPersonId(Long id);
    void addSkillToPerson(Long skillId, Long personId, int skillCost);

    void removeSkillFromPerson(Long skillId, Long personId, int skillCost);
    List<Person> showPeopleBySkillId(Long skillId);
}

