package com.vigulear.spring.mvc_hibernate_aop.dao.impl;

import com.vigulear.spring.mvc_hibernate_aop.aspect.exception_handling.exceptions.AlreadyPresentException;
import com.vigulear.spring.mvc_hibernate_aop.dao.SkillDao;
import com.vigulear.spring.mvc_hibernate_aop.entity.Person;
import com.vigulear.spring.mvc_hibernate_aop.entity.Skill;
import jakarta.persistence.*;

import org.springframework.stereotype.Repository;

@Repository
public class SkillDaoImpl extends AbstractDaoImpl<Skill> implements SkillDao {

  @PersistenceContext private EntityManager entityManager;
  private Skill persistCriteriaSkill;

  @Override
  protected Class<Skill> getEntityClass() {
    return Skill.class;
  }

  @Override
  public Skill persist(Skill skill) {

    Skill skillToPersist = this.findByEntity(skill);

    if (skillToPersist == null) {
      if (this.skillIsValidToPersist(skill)) {
        entityManager.persist(skill);
        return skill;
      } else {
        throw new AlreadyPresentException(
            "There is already skill with such name: "
                + persistCriteriaSkill.getName()
                + "."
                + persistCriteriaSkill.getDomain());
      }
    }
    return skillToPersist;
  }

  @Override
  public Skill update(Skill skill) {
    skill = entityManager.find(Skill.class, skill.getId());

    entityManager.merge(skill);

    return skill;
  }

  @Override
  public Person findPersonById(Long id) {
    Query query = entityManager.createQuery("FROM Person p where p.id = :id");
    query.setParameter("id", id);

    return (Person) query.getSingleResult();
  }

  @Override
  public Skill findByEntity(Skill skill) {
    Skill querriedSkill;
    try {
      Query query =
          entityManager.createQuery(
              "FROM Skill s where s.name = :name and s.domain = :domain and s.level = :level");
      query.setParameter("name", skill.getName());
      query.setParameter("domain", skill.getDomain());
      query.setParameter("level", skill.getLevel());

      querriedSkill = (Skill) query.getSingleResult();
    } catch (NoResultException exception) {
      return null;
    }

    return querriedSkill;
  }

  @Override
  public Skill findByName(String name) {
    Skill querriedSkill;

    try {
      Query query = entityManager.createQuery("FROM Skill s WHERE s.name = :name");
      query.setParameter("name", name);

      querriedSkill = (Skill) query.getResultList().get(0);
    } catch (IndexOutOfBoundsException exception) {
      return null;
    }

    return querriedSkill;
  }

  @Override
  public void deleteById(Long id) {

    Skill skillToDelete = this.findById(id);

    entityManager
        .createNativeQuery(
            "UPDATE PERSONS p SET p.TOTAL_COST = p.TOTAL_COST - ? WHERE p.ID IN (SELECT ps.USER_ID FROM PERSONS_SKILLS ps WHERE ps.SKILL_ID = ?)")
        .setParameter(1, skillToDelete.getSkillCost())
        .setParameter(2, skillToDelete.getId())
        .executeUpdate();

    entityManager
        .createNativeQuery("DELETE FROM PERSONS_SKILLS ps where ps.SKILL_ID = ?")
        .setParameter(1, id)
        .executeUpdate();

    super.deleteById(id);
  }

  /*
  If there is NO skill with the same name then new skill is valid to persist
  If there IS a skill with the same name it MUST be of the same domain otherwise
   */
  private boolean skillIsValidToPersist(Skill skill) {
    persistCriteriaSkill = this.findByName(skill.getName());
    if (persistCriteriaSkill == null) return true;
    return skill.getDomain().equals(persistCriteriaSkill.getDomain());
  }
}
