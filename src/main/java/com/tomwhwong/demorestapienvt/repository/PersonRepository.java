package com.tomwhwong.demorestapienvt.repository;

import com.tomwhwong.demorestapienvt.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

  public Person findByName(String name);

}
