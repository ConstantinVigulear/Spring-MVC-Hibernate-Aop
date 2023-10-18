package com.vigulear.spring.mvc_hibernate_aop.dao;

import com.vigulear.spring.mvc_hibernate_aop.entity.Person;
import com.vigulear.spring.mvc_hibernate_aop.entity.Skill;
import com.vigulear.spring.mvc_hibernate_aop.entity.SkillDomain;

public interface SkillDao extends GenericDao<Skill> {
    Person findPersonById(Long id);

    Skill findByEntity(Skill skill);
    Skill findByName(String name);
}
