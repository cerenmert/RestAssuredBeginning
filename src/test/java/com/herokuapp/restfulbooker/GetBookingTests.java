package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTests {
    @Test
    public void getBookingIdsWithoutFilterTest() {
        //Get response for specific booking id
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/5");
        response.print();

        //Verify response is 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        //Verify all fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Mary", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Smith", "lastname in response is not expected");

        int price= response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 446,"total price in response is not expected");

        boolean depositpaid= response.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositpaid, "depositpaid should be false, but it is true");

        String actualCheckIn= response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn,"2021-01-12","CheckIn in response is not as expected");

        String actualCheckOut= response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut,"2021-02-28", "CheckOut in response is not expected");

        softAssert.assertAll();  // if we didn't add this, test result can be false positive. It will be passed.
    }
}
