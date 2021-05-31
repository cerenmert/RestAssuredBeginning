package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBookingTests extends BaseTest {
    @Test
    public void deleteBookingTest() {
        //Create new booking
        Response createResponse = createBooking();
        createResponse.print();

        //Get ID for the booking you want to update
        int bookingID = createResponse.jsonPath().getInt("bookingid");

        // Delete booking by using auth token
        Response responseDelete = RestAssured.given(spec)
                .auth().preemptive().basic("admin", "password123")
                .delete("/booking/" + bookingID);
        responseDelete.print();

        //Assert that status is 201
        Assert.assertEquals(responseDelete.getStatusCode(), 201, "Status code should be 201, but it's not");

        //Verify that deleted booking is not exist anymore
        Response responseGet = RestAssured.given(spec).get("/booking/" + bookingID);
        Assert.assertEquals(responseGet.getBody().asString(), " ", "Body should be Not Found but it is not");

    }
}
