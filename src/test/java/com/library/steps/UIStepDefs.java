package com.library.steps;

import io.cucumber.java.en.Then;

public class UIStepDefs {

    @Then("created user should be able to login Library UI")
    public void created_user_should_be_able_to_login_library_ui() {
        System.out.println("STEP 2");

    }
    @Then("created user name should appear in Dashboard Page")
    public void created_user_name_should_appear_in_dashboard_page() {
        System.out.println("STEP 3");
    }
}
