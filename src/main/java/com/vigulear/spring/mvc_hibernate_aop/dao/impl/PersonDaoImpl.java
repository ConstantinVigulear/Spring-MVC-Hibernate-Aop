package com.vigulear.spring.mvc_hibernate_aop.dao.impl;

import com.vigulear.spring.mvc_hibernate_aop.dao.PersonDao;
import com.vigulear.spring.mvc_hibernate_aop.entity.Person;
import com.vigulear.spring.mvc_hibernate_aop.entity.Skill;
import com.vigulear.spring.mvc_hibernate_aop.aspect.exception_handling.exceptions.AlreadyPresentException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

import jakarta.persistence.Query;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDaoImpl extends AbstractDaoImpl<Person> implements PersonDao {

  @PersistenceContext private EntityManager entityManager;

  @Override
  protected Class<Person> getEntityClass() {
    return Person.class;
  }

  @Override
  public Person persist(Person person) {
    if (this.findByEmail(person.getEmail()) == null) {
      entityManager.persist(person);
    } else
      throw new AlreadyPresentException(
          "Person '"
              + person.getName()
              + " "
              + person.getSurname()
              + "' with email '"
              + person.getEmail()
              + "' is already present");

    return person;
  }

  @Override
  public Person update(Person person) {

    if (isDifferentPersonWithThisEmailAlreadyPresent(person)) {
      throw new AlreadyPresentException(
          "Person with such email (" + person.getEmail() + ") is already present");
    } else {
      Person personToUpdate = entityManager.find(Person.class, person.getId());

      personToUpdate
          .setName(person.getName())
          .setSurname(person.getSurname())
          .setEmail(person.getEmail());
      entityManager.merge(personToUpdate);
    }

    return person;
  }

  @Override
  public Person findByEmail(String email) {
    Person queriedPerson;

    try {
      Query query = entityManager.createQuery("FROM Person p where p.email = :email");
      query.setParameter("email", email);

      queriedPerson = (Person) query.getSingleResult();
    } catch (NoResultException exception) {
      return null;
    }

    return queriedPerson;
  }

  @Override
  public List<Person> showPeopleBySkillId(Long skillId) {
    return entityManager
        .createQuery("FROM Person p inner join p.skills s where s.id = :skillId")
        .setParameter("skillId", skillId)
        .getResultList();
  }

  @Override
  public List<Skill> findSkillsByPersonId(Long id) {
    Query query = entityManager.createQuery("FROM Skill s INNER JOIN s.persons p where p.id = :id");
    query.setParameter("id", id);
    return query.getResultList();
  }

  @Override
  public void addSkillToPerson(Long skillId, Long personId, int skillCost) {
    Person person = super.findById(personId);

    removeSameSkillWithDifferentLevel(skillId, personId);

    try {
      entityManager
          .createNativeQuery("INSERT INTO PERSONS_SKILLS VALUES (?, ?)")
          .setParameter(1, skillId)
          .setParameter(2, personId)
          .executeUpdate();
    } catch (ConstraintViolationException exception) {
      throw new AlreadyPresentException("This person already has this skill");
    }
    person.setTotalCost(person.getTotalCost() + skillCost);
    entityManager.merge(person);
  }

  @Override
  public void removeSkillFromPerson(Long skillId, Long personId, int skillCost) {
    Person person = super.findById(personId);

    entityManager
        .createNativeQuery("DELETE FROM PERSONS_SKILLS where SKILL_ID = ? and USER_ID  = ?")
        .setParameter(1, skillId)
        .setParameter(2, personId)
        .executeUpdate();

    person.setTotalCost(person.getTotalCost() - skillCost);
    entityManager.merge(person);
  }

  private void removeSameSkillWithDifferentLevel(Long skillId, Long personId) {
    Skill skillToAdd = entityManager.find(Skill.class, skillId);

    this.findSkillsByPersonId(personId).stream()
        .filter(
            skill ->
                skill.getName().equals(skillToAdd.getName())
                    && skill.getDomain().equals(skillToAdd.getDomain()))
        .findAny()
        .ifPresent(
            skillFromPersonSkillSet -> {
              if (!skillToAdd.getLevel().equals(skillFromPersonSkillSet.getLevel())) {
                this.removeSkillFromPerson(
                    skillFromPersonSkillSet.getId(),
                    personId,
                    skillFromPersonSkillSet.getSkillCost());
              }
            });
  }

  private boolean isDifferentPersonWithThisEmailAlreadyPresent(Person person) {

    Person personByEmail = this.findByEmail(person.getEmail());

    if (personByEmail == null) {
      return false;
      // if two persons have the same email but different id means such email is already taken
    } else return !person.getId().equals(personByEmail.getId());
  }
}
