package com.library.steps;

import com.library.utility.ConfigurationReader;
import com.library.utility.DB_Util;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.keyStore;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class APIStepDefs {

    RequestSpecification givenPart;
    Response response;
    ValidatableResponse thenPart;
    Response addRequest;
    Map<String,Object> newBookPost;
    Map<String,Object> newUserPost;
    static Map<String,Object> mapRequest;
    static int userId;
     /**
     * US 01 RELATED STEPS
     *
     */
    @Given("I logged Library api as a {string}")
    public void i_logged_library_api_as_a(String userType) {

        givenPart = given().log().uri()
                .header("x-library-token", LibraryAPI_Util.getToken(userType));
    }

    @Given("Accept header is {string}")
    public void accept_header_is(String contentType) {
        givenPart.accept(contentType);
    }

    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {
        response = givenPart.when().get(ConfigurationReader.getProperty("library.baseUri") + endpoint).prettyPeek();
        thenPart = response.then();
    }

    @Then("status code should be {int}")
    public void status_code_should_be(Integer statusCode) {
        thenPart.statusCode(statusCode);

    }

    @Then("Response Content type is {string}")
    public void response_content_type_is(String contentType) {
        thenPart.contentType(contentType);
    }

    @Then("{string} field should not be null")
    public void field_should_not_be_null(String path) {
        thenPart.body(path, is(notNullValue()));
    }

    @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String contentType) {
        givenPart.contentType(contentType);
    }

    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String request) {

        switch (request){
            case "book":
                newBookPost = LibraryAPI_Util.getRandomBookMap();
                mapRequest = newBookPost;
                break;
            case "user":
                newUserPost = LibraryAPI_Util.getRandomUserMap();
                mapRequest = newUserPost;

        }
        System.out.println("mapRequest = " + mapRequest);
    }

    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endpoint) {

            addRequest = givenPart
                    .and()
                    .contentType("application/x-www-form-urlencoded")
                    .formParams(mapRequest)
                    .when()
                    .post(ConfigurationReader.getProperty("library.baseUri") + endpoint).prettyPeek();
            thenPart = addRequest.then();
    }

    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String string, String string2) {

        addRequest.then().body(string, is(string2));
        JsonPath jsonPath = addRequest.jsonPath();
        userId = jsonPath.getInt("user_id");
    }


}


