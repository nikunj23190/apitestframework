package com.automationtest.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.assertj.core.util.Arrays;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.automationtest.assignment.config.AppConfig;
import com.automationtest.assignment.domain.request.CreateBookingRequestPayload;
import com.automationtest.assignment.domain.response.BookingId;
import com.automationtest.assignment.domain.response.CreateBookingResponse;
import com.automationtest.assignment.domain.response.UpdateBookingResponse;
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
public class ApiTests extends HttpOperations{
	
	private int booingId;
	private Response response;

	
	
//	@Test
	@Tag("Regression")
	void deleteBookingTest() throws IOException {

		//GIVEN
		createBooking();
		
		//WHEN
		response = deleteRequest(url + "/" + booingId , commonHeaders);
		
		//THEN
		assertEquals(response.statusCode(), HttpStatus.SC_CREATED);
		
		
	}
	
	
	//@Test
	@Tag("Regression")
	void getBookingIdsTest() throws IOException {

		//GIVEN
		createBooking();
		
		BookingId generatedBooking = new BookingId();
		generatedBooking.setBookingid(booingId);
		
		//WHEN
		response = getRequest(url, commonHeaders);
		
		//THEN
		assertEquals(response.statusCode(), HttpStatus.SC_OK);
		BookingId allBookingIdsResponse[] = gson.fromJson(response.asString(), BookingId[].class);
		
		assertEquals(true,allBookingIdsResponse.length>1);
		
		//Validate if newly created Booking is added in the list
		List<Object> allBookingIdList = Arrays.asList(allBookingIdsResponse);
		assertEquals(true,allBookingIdList.contains(generatedBooking));
		
	}
	
	
	
	@Test
	@Tag("Regression")
	void partialUpdateBookingTest() throws IOException {

		//GIVEN
		createBooking();
		
		JSONObject patchRequest = new JSONObject();
		patchRequest.put("firstname", "UpdatedFirstName");
		patchRequest.put("lastname", "Updatedlastname");
		
		//WHEN
		response = patchRequest(url +"/"+ booingId ,patchRequest.toString(), commonHeaders);
		
		//THEN
		assertEquals(response.statusCode(), HttpStatus.SC_OK);
		
		UpdateBookingResponse updateBookingResponse = gson.fromJson(response.asString(), UpdateBookingResponse.class);
		assertEquals(patchRequest.get("firstname"), updateBookingResponse.getFirstname());
		assertEquals(patchRequest.get("lastname"), updateBookingResponse.getLastname());
		

	}
	

	private void createBooking() throws IOException {

		CreateBookingRequestPayload bookingRequestPayload = Resources.getFileAsObject("json-requests/createBooking.json", CreateBookingRequestPayload.class);
		response = postRequest(url, gson.toJson(bookingRequestPayload), commonHeaders);
		
		CreateBookingResponse bookingResponse = gson.fromJson(response.asString(), CreateBookingResponse.class);
		booingId = bookingResponse.getBookingid();
		
		assertEquals(response.statusCode(), HttpStatus.SC_OK);
		
	}

	
	
}
