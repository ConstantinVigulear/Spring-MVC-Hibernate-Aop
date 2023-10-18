package com.vigulear.spring.mvc_hibernate_aop.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "persons")
public class Person {

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "surname", nullable = false)
  private String surname;

  @Column(name = "email", nullable = false)
  private String email;

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinTable(
      name = "persons_skills",
      joinColumns = @JoinColumn(name = "user_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "skill_id", nullable = false))
  private Set<Skill> skills = new HashSet<>();

  @Column(name = "total_cost", nullable = false)
  private int totalCost = 0;

  public Long getId() {
    return id;
  }

  public Person setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Person setName(String name) {
    this.name = name;
    return this;
  }

  public String getSurname() {
    return surname;
  }

  public Person setSurname(String surname) {
    this.surname = surname;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public Person setEmail(String email) {
    this.email = email;
    return this;
  }

  public Set<Skill> getSkills() {
    return skills;
  }

  public void setTotalCost(int totalCost) {
    this.totalCost = totalCost;
  }

  public int getTotalCost() {
    return totalCost;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(getName(), person.getName())
        && Objects.equals(getSurname(), person.getSurname())
        && Objects.equals(getEmail(), person.getEmail());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getSurname(), getEmail());
  }
}
