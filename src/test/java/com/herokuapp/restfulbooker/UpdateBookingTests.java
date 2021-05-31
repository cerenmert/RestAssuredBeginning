package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTests extends BaseTest{
    @Test
    public void updateBookingTest(){
        //Create new booking
        Response responseCreate = createBooking(); //this method is in BaseTest
        responseCreate.print();

        //Get bookingId of new booking
        int bookingID= responseCreate.jsonPath().getInt("bookingid");

        //Create Json body
        JSONObject body = new JSONObject();
        body.put("firstname", "Elif");
        body.put("lastname", "Mert");
        body.put("totalprice", "168");
        body.put("depositpaid", true);
        body.put("additionalneeds", "Baby crib");

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2021-05-31");
        bookingdates.put("checkout", "2021-06-02");
        body.put("bookingdates", bookingdates); //we add "bookingdates jsonObject" to body

        //Update Response by using auth.
        Response responseUpdate = RestAssured.given().
                auth().preemptive().basic("admin","password123").
                contentType(ContentType.JSON).body(body.toString()).
                put("https://restful-booker.herokuapp.com/booking/"  +bookingID);
        responseUpdate.print();

        //Verify response is 200
        Assert.assertEquals(responseUpdate.getStatusCode(),200,"Status code should be 200, but it is not");

        //Verify all fields
        SoftAssert softAssert= new SoftAssert();
        String actualFirstName= responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName,"Elif","firstname in response is not as expected");
        String actualLastName= responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName,"Mert","lastname in response is not as expected");
        int price= responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price,168,"price is not as expected");
        boolean depositpaid= responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositpaid, "depositpaid should be true, but it is false");
        String actualCheckIn= responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn,"2021-05-31","CheckIn in response is not as expected");
        String actualCheckOut= responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut,"2021-06-02", "CheckOut in response is not as expected");
        String actualAdditionalNeeds= responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds,"Baby crib", "Additional need is different");
        softAssert.assertAll();
    }
}
