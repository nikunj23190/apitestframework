package com.automationtest.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.automationtest.assignment.config.AppConfig;
import com.automationtest.assignment.domain.request.CreateBookingRequestPayload;
import com.automationtest.assignment.domain.response.CreateBookingResponse;
import com.automationtest.assignment.httpoperatonimpl.HttpOperations;
import com.automationtest.assignment.utils.Resources;
import com.google.gson.Gson;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

@SpringBootTest
@ContextConfiguration(classes = {AppConfig.class})
public class SampleTest extends HttpOperations{
	
	private int booingId;
	private Response response;

	private RequestSpecification request = given().contentType("application/json").header("Content-Type","application/json").log().all();;
	private ResponseSpecification responseSpec;
	
	@Autowired
	private Gson gson;
	
	

	@Test
	void sampletest() throws IOException {
		
		CreateBookingRequestPayload bookingRequestPayload = Resources.getFileAsObject("json-requests/createBooking.json", CreateBookingRequestPayload.class);
		response = postRequest(url, gson.toJson(bookingRequestPayload), commonHeaders);
		
		CreateBookingResponse bookingResponse = gson.fromJson(response.asString(), CreateBookingResponse.class);
		
		booingId = bookingResponse.getBookingid();
		
		assertEquals(response.statusCode(), HttpStatus.SC_OK);
		
		//assertEquals(response.statusCode(), 200);
		//System.out.println(response.prettyPrint());
	}

}
