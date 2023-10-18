package com.vigulear.spring.mvc_hibernate_aop.dao;

import com.vigulear.spring.mvc_hibernate_aop.entity.Person;
import com.vigulear.spring.mvc_hibernate_aop.entity.Skill;

import java.util.List;

public interface PersonDao extends GenericDao<Person> {

    List<Skill> findSkillsByPersonId(Long id);
    void addSkillToPerson(Long skillId, Long personId, int skillCost);
    void removeSkillFromPerson(Long skillId, Long personId, int skillCost);

    Person findByEmail(String email);
    List<Person> showPeopleBySkillId(Long skillId);
}
