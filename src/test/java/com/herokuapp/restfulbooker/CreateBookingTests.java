package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTests extends BaseTest{
    @Test
    public void createBookingTest(){
        Response response = createBooking(); //this method is in BaseTest
        response.print();

        //Verify response is 200
        Assert.assertEquals(response.getStatusCode(),200,"Status code should be 200, but it is not");

        //Verification for fields
        SoftAssert softAssert= new SoftAssert();
        String actualFirstName= response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName,"Ceren","firstname in response is not as expected");
        String actualLastName= response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName,"Mert","lastname in response is not as expected");
        int price= response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(price,125,"price is not as expected");
        boolean depositpaid= response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(depositpaid, "depositpaid should be false, but it is true");
        String actualCheckIn= response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn,"2021-05-31","CheckIn in response is not as expected");
        String actualCheckOut= response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut,"2021-06-02", "CheckOut in response is not as expected");
        String actualAdditionalNeeds= response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds,"Baby crib", "Additional need is different");
        softAssert.assertAll();
    }
}
