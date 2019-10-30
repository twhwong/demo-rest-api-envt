package com.tomwhwong.demorestapienvt.controller;

import com.tomwhwong.demorestapienvt.model.Person;
import com.tomwhwong.demorestapienvt.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {

  @Autowired
  private PersonRepository personRepository;

  /**
   * create a person
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Person> createUser(@Valid @RequestBody Person person) {
    // save the object
    person = personRepository.save(person);

    // Set the location header for the newly created resource
    HttpHeaders responseHeaders = new HttpHeaders();
    URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(person.getId())
            .toUri();
    responseHeaders.setLocation(newPollUri);
    //System.out.println("Person created: " + person.toString());
    return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
  }

  /**
   * create multiple persons
   */
  @RequestMapping(value = "/list", method = RequestMethod.POST)
  public ResponseEntity<List<Person>> createPersons(@Valid @RequestBody List<Person> persons) {
    for (Person person : persons) {
      person = personRepository.save(person);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Get all persons
   */
  // @RequestMapping(value = "/persons", method = RequestMethod.GET, produces = "application/json")
  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  // specifies what it produces, otherwise default */* will be shown in swagger UI
  public ResponseEntity<Iterable<Person>> getAllPersons() {
    Iterable<Person> allPersons = personRepository.findAll();
    return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);
  }

  /**
   * Verify if person with this id exits
   */
  protected void verifyPerson(Long personId) throws ResourceNotFoundException {
    Person person = personRepository.findById(personId).orElse(null);
    if (person == null) {
      // own custom exception here
      throw new ResourceNotFoundException("Person with id " + personId + " not found!");
    }

  }

  /**
   * Verify if person with this name exits
   */
  protected void verifyPerson(String name) throws ResourceNotFoundException {
    Person person = personRepository.findByName(name);
    if (person == null) {
      // own custom exception here
      throw new ResourceNotFoundException("Person with name " + name + " not found!");
    }
  }

  /**
   * Get a person with an id
   */
  @RequestMapping(value = "/id/{personId}", method = RequestMethod.GET)
  public ResponseEntity<Person> getPerson(@PathVariable Long personId) {
    // check if person id exists
    verifyPerson(personId);
    //
    Person person = personRepository.findById(personId).orElse(null);
    return new ResponseEntity<>(person, HttpStatus.OK);
  }

  /**
   * Find person by name
   */
  @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
  public ResponseEntity<Person> getPerson(@PathVariable String name) {
    // check if person's name exists
    verifyPerson(name);
    //
    Person person = personRepository.findByName(name);
    return new ResponseEntity<>(person, HttpStatus.OK);
  }

  /**
   * Update a person by id
   */
  @RequestMapping(value = "/id/{personId}", method = RequestMethod.PUT)
  public ResponseEntity<Person> updatePerson(@Valid @RequestBody Person person, @PathVariable Long personId) {
    // check if it exists
    verifyPerson(personId);
    Person currentPerson = personRepository.findById(personId).orElse(null);
    if (currentPerson == null) {
      return new ResponseEntity<>(person, HttpStatus.NOT_FOUND);
    }
    // set id in order to update the object
    person.setId(personId);
    personRepository.save(person);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Delete a person with an id
   */
  @RequestMapping(value = "/id/{personId}", method = RequestMethod.DELETE)
  public ResponseEntity<Person> deletePerson(@PathVariable Long personId) {
    verifyPerson(personId);
    personRepository.deleteById(personId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
