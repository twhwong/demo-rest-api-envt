package com.tomwhwong.demorestapienvt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Entity
@Table(name = "PERSONS")
public class Person {

  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Long id;

  @NotNull
  @Column(name = "NAME")
  private String name;

  @JsonFormat(pattern = "dd-MM-yyyy")
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @Past
  @Column(name = "BIRTHDAY")
  private Date birthday;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  @Override
  public String toString() {
    return "Person{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", birthday=" + birthday +
            '}';
  }
}
