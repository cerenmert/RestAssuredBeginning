package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PartialUpdateBookingTests extends BaseTest{
    @Test
    public void partialUpdateBookingTest(){
        //Create new booking
        Response createResponse= createBooking();
        createResponse.print();

        //Get ID for the booking you want to update
        int bookingID= createResponse.jsonPath().getInt("bookingid");

        //Create JSON body to update fields
        JSONObject body= new JSONObject();
        body.put("firstname", "Berfin");

        JSONObject bookingDates= new JSONObject();
        bookingDates.put("checkin","2021-07-07");
        bookingDates.put("checkout","2021-07-17");

        body.put("bookingdates",bookingDates); // this is important. do not forget!

        //Partial Update booking by using auth token
        Response responsePartialUpdate= RestAssured.given(spec)
                .auth().preemptive().basic("admin","password123")
                .contentType(ContentType.JSON)
                .body(body.toString())
                .patch("/booking/"+bookingID);
        responsePartialUpdate.print();

        //Assert that status is 200
        Assert.assertEquals(responsePartialUpdate.getStatusCode(),200, "Status code should be 200");

        //Assert that all fields are correct
        SoftAssert softAssert= new SoftAssert();
        String actualFirstName=responsePartialUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName,"Berfin","firstname in response is not as expected");
        String actualLastName= responsePartialUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName,"Mert","lastname in response is not as expected");
        int price= responsePartialUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price,125,"price is not as expected");
        boolean depositpaid= responsePartialUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositpaid, "depositpaid should be false, but it is true");
        String actualCheckIn= responsePartialUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn,"2021-07-07","CheckIn in response is not as expected");
        String actualCheckOut= responsePartialUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut,"2021-07-17", "CheckOut in response is not as expected");
        String actualAdditionalNeeds= responsePartialUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds,"Baby crib", "Additional need is different");
        softAssert.assertAll();
    }
}
