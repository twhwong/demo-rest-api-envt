package com.tomwhwong.demorestapienvt.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;


@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
class PersonControllerTest {
  /**
   * Make sure that the spring boot application is running before testing.
   */

  private String validUsername = "tom";
  private String validPassword = "abc123";
  private String baseURI = "http://localhost:8090/api";


  @Test
  public void basicAuthentication_validUser_Test() {
    RestAssured.baseURI = baseURI;
    Response response = null;

    // Scenario with valid user
    response = given().auth().basic(validUsername, validPassword)
            .when().get("/persons");
    System.out.println("Access Authorized \nStatus code: " + response.getStatusCode());
    System.out.println("Response:" + response.asString());
    assertEquals(200, response.getStatusCode());

  }
  @Test
  public void basicAuthentication_invalidUser_Test() {
    RestAssured.baseURI = baseURI;
    Response response = null;

    String invalidUsername = "testuser";
    String invalidPassword = "abc";

    // Scenario with invalid user
    response = given().auth().basic(invalidUsername, invalidPassword)
            .when().get("/persons");
    System.out.println("Access Unauthorized \nStatus code: " + response.getStatusCode());
    System.out.println("Response:" + response.asString());
    assertEquals(401, response.getStatusCode());

  }

  @Test
  public void postRequestTest() {
    RestAssured.baseURI = baseURI;
    Response response = null;

    String payload = "{\n" +
            "  \"name\": \"John\",\n" +
            "  \"birthday\": \"05-11-1981\"\n" +
            "}";

    response = given().auth().basic(validUsername, validPassword)
            .when()
            .contentType(ContentType.JSON)
            .body(payload)
            .post("/persons");
    System.out.println("Response:" + response.body().asString());
    System.out.println("Status code: " + response.getStatusCode());

    assertEquals(201, response.getStatusCode()); // created ?


  }

}