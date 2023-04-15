package com.library.steps;

import com.library.utility.DB_Util;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.sql.ResultSet;
import java.util.Map;

import static org.junit.Assert.*;

public class DBStepDefs {

    @Then("created user information should match with Database")
    public void created_user_information_should_match_with_database() {

        int userId = APIStepDefs.userId;
        DB_Util.runQuery("select * from users\n" +
                "where id = " + userId + ";");


        Map<String, Object> DBmap = DB_Util.getRowMap(1);
        System.out.println("DBmap = " + DBmap);
        String DBfull_name = (String) DBmap.get("full_name");
        String DBemail = (String) DBmap.get("email");
        String DBaddress = (String) DBmap.get("address");

        System.out.println("APIStepDefs.mapRequest = " + APIStepDefs.mapRequest);

        assertEquals(DBemail, APIStepDefs.mapRequest.get("email"));
        assertEquals(DBfull_name, APIStepDefs.mapRequest.get("full_name"));
        assertEquals(DBaddress, APIStepDefs.mapRequest.get("address"));


    }



}
