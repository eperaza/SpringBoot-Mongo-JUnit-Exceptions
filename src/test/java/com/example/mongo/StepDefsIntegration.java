package com.example.mongo;

import org.springframework.http.HttpStatus;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.greaterThan;
import org.json.*;
import org.mockito.internal.matchers.GreaterThan;
import java.util.List;
import com.example.mongo.model.User;

public class StepDefsIntegration extends SpringIntegration {
    
    @Given("the client calls /findall")
    public void the_client_calls_findall() throws Throwable {
        executeGet("https://localhost:8443/api/findall");
    }

    @When("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @Then("the client receives size of {int}")
    public void the_client_receives_size_of(Integer size) throws Throwable {
        JSONArray jsnobject = new JSONArray(latestResponse.getBody());
        System.out.println(size);
        assertThat(jsnobject.length(), greaterThan(0)); 
    }
}
