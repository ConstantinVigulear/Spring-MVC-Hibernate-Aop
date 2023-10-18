package com.vigulear.spring.mvc_hibernate_aop.service;

import com.vigulear.spring.mvc_hibernate_aop.dao.GenericDao;
import com.vigulear.spring.mvc_hibernate_aop.dao.SkillDao;
import com.vigulear.spring.mvc_hibernate_aop.entity.Person;
import com.vigulear.spring.mvc_hibernate_aop.entity.Skill;
import com.vigulear.spring.mvc_hibernate_aop.service.SkillService;
import jakarta.transaction.Transactional;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class SkillServiceImpl implements SkillService {

  private final SkillDao skillDao;

  public SkillServiceImpl(SkillDao skillDao) {
    this.skillDao = skillDao;
  }

  @Override
  public Skill persist(Skill entity) {
    return skillDao.persist(entity);
  }

  @Override
  public Skill update(Skill entity) {
    return skillDao.update(entity);
  }

  @Override
  public Skill findById(Long id) {
    return skillDao.findById(id);
  }

  @Override
  public List<Skill> findAll() {
    return skillDao.findAll();
  }

  @Override
  public void delete(Skill entity) {
    skillDao.delete(entity);
  }

  @Override
  public void deleteById(Long id) {
    skillDao.deleteById(id);
  }

  @Override
  public Person findPersonById(Long id) {
    return skillDao.findPersonById(id);
  }
}
