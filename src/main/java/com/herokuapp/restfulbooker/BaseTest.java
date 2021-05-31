package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected  RequestSpecification spec;

    @BeforeMethod
    public void setUp(){
        spec= new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").build();
    }

    protected Response createBooking() {
        //Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "Ceren");
        body.put("lastname", "Mert");
        body.put("totalprice", "125");
        body.put("depositpaid", false);
        body.put("additionalneeds", "Baby crib");

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2021-05-31");
        bookingdates.put("checkout", "2021-06-02");
        body.put("bookingdates", bookingdates); //we add "bookingdates jsonObject" to body

        //Get Response
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(body.toString()).post("/booking");
        return response;
    }
}
