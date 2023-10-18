package com.vigulear.spring.mvc_hibernate_aop.service;

import com.vigulear.spring.mvc_hibernate_aop.entity.Person;
import com.vigulear.spring.mvc_hibernate_aop.entity.Skill;

import java.util.List;

public interface SkillService extends GenericService<Skill> {
    Skill findById(Long id);
    Person findPersonById(Long id);
}
