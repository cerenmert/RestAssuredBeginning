package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdsTests extends BaseTest{
    @Test
    public void getBookingIdsWithoutFilterTest(){
        //Get response with booking Ids
        Response response= RestAssured.given(spec).get("/booking");
        response.print();
        //Verify response is 200
        Assert.assertEquals(response.getStatusCode(), 200,"Status code should be 200, but it's not");
        //Verify at least one booking in response
        List<Integer>bookingIds= response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(),"List of bookingIds is empty, but it shouldn't be");
    }
    @Test
    public void getBookingIdsWithFilterByNameTest(){

        //add query parameter to spec
        spec.queryParam("firstname","Jim");
        spec.queryParam("lastname","Smith");

        //Get response - filter by name and lastname
        Response response= RestAssured.given(spec).get("/booking");
        response.print();
        Assert.assertEquals(response.getStatusCode(),200,"It should be 200");
        List<Integer>bookingIds= response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of bookingIds is empty,but it shouldn't be");

    }
    @Test
    public void getBookingIdsWithFilterByDateTest(){

        //add query parameter to spec
        spec.queryParam("checkin","2020-04-30");
        spec.queryParam("checkout","2021-03-23");

        //Get response - filter by name and lastname
        Response response= RestAssured.given(spec).get("/booking");
        response.print();
        Assert.assertEquals(response.getStatusCode(),200,"It should be 200");
        List<Integer>bookingIds= response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of bookingIds is empty,but it shouldn't be");

    }
}
