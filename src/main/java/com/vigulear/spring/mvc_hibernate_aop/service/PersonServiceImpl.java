package com.vigulear.spring.mvc_hibernate_aop.service;

import com.vigulear.spring.mvc_hibernate_aop.dao.PersonDao;
import com.vigulear.spring.mvc_hibernate_aop.entity.Person;
import com.vigulear.spring.mvc_hibernate_aop.entity.Skill;
import jakarta.transaction.Transactional;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

  private final PersonDao personDao;

  public PersonServiceImpl(PersonDao personDao) {
    this.personDao = personDao;
  }

  @Override
  public Person persist(Person entity) {
    return personDao.persist(entity);
  }

  @Override
  public Person update(Person entity) {
    return personDao.update(entity);
  }

  @Override
  public Person findById(Long id) {
    return personDao.findById(id);
  }

  @Override
  public List<Person> findAll() {
    return personDao.findAll();
  }

  @Override
  public void delete(Person entity) {
    personDao.delete(entity);
  }

  @Override
  public void deleteById(Long id) {
    personDao.deleteById(id);
  }

  @Override
  public List<Skill> findSkillsByPersonId(Long id) {
    return personDao.findSkillsByPersonId(id);
  }

  @Override
  public void addSkillToPerson(Long skillId, Long personId, int skillCost) {
    personDao.addSkillToPerson(skillId, personId, skillCost);
  }

  @Override
  public void removeSkillFromPerson(Long skillId, Long personId, int skillCost) {
    personDao.removeSkillFromPerson(skillId, personId, skillCost);
  }

  @Override
  public List<Person> showPeopleBySkillId(Long skillId) {
    return personDao.showPeopleBySkillId(skillId);
  }
}
