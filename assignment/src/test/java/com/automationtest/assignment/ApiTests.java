package com.automationtest.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpStatus;
import org.assertj.core.util.Arrays;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ContextConfiguration;

import com.automationtest.assignment.config.AppConfig;
import com.automationtest.assignment.domain.request.CreateBookingRequestPayload;
import com.automationtest.assignment.domain.response.BookingId;
import com.automationtest.assignment.domain.response.CreateBookingResponse;
import com.automationtest.assignment.domain.response.GetBookingByIdResponse;
import com.automationtest.assignment.domain.response.UpdateBookingResponse;
import com.automationtest.assignment.httpoperatonimpl.HttpOperations;
import com.automationtest.assignment.utils.Resources;

import io.restassured.response.Response;

@SpringBootTest
@ContextConfiguration(classes = {AppConfig.class})
public class ApiTests extends HttpOperations{
	
	private int booingId;
	private Response response;

	
	@Test
	@Tag("Regression")
	@Description("Verify createBooking service returns 200 response and new booking id is generated")
    void createBookingTest() throws IOException {

		//GIVEN
		CreateBookingRequestPayload bookingRequestPayload = Resources.getFileAsObject("json-requests/createBooking.json", CreateBookingRequestPayload.class);
		
		//WHEN
		response = postRequest(url, gson.toJson(bookingRequestPayload), commonHeaders);
		
		CreateBookingResponse bookingResponse = gson.fromJson(response.asString(), CreateBookingResponse.class);
		booingId = bookingResponse.getBookingid();
		
		//THEN
		assertEquals(response.statusCode(), HttpStatus.SC_OK);
		
	}
	
	@Test
	@Tag("Regression")
	@Description("Verify getBookingyID service returns newly created booking details")
	void getBookingByIdTest() throws IOException {

		//GIVEN
		CreateBookingRequestPayload bookingRequestPayload = Resources.getFileAsObject("json-requests/createBooking.json", CreateBookingRequestPayload.class);
		response = postRequest(url, gson.toJson(bookingRequestPayload), commonHeaders);
		
		CreateBookingResponse bookingResponse = gson.fromJson(response.asString(), CreateBookingResponse.class);
		booingId = bookingResponse.getBookingid();
		
		assertEquals(response.statusCode(), HttpStatus.SC_OK);
		
		
		//WHEN
		response = getRequest(url + "/" + booingId, commonHeaders);
		
		GetBookingByIdResponse getBookingResponse = gson.fromJson(response.asString(), GetBookingByIdResponse.class);
		
		assertEquals(response.statusCode(), HttpStatus.SC_OK);
		assertEquals(bookingRequestPayload.getFirstname(), getBookingResponse.getFirstname());
		assertEquals(bookingRequestPayload.getLastname(), getBookingResponse.getLastname());
		assertEquals(bookingRequestPayload.getTotalprice(), getBookingResponse.getTotalprice());
		assertEquals(bookingRequestPayload.getDepositpaid(), getBookingResponse.getDepositpaid());
		assertEquals(bookingRequestPayload.getAdditionalneeds(), getBookingResponse.getAdditionalneeds());	
		
	}
	
	
	
	@Test
	@Tag("Regression")
	@Description("Verify deleteBooking service returns 201")
	void deleteBookingTest() throws IOException {

		//GIVEN
		createBooking();
		
		//WHEN
		response = deleteRequest(url + "/" + booingId , commonHeaders);
		
		//THEN
		assertEquals(response.statusCode(), HttpStatus.SC_CREATED);
		
		
	}
	
	
	@Test
	@Tag("Regression")
	@Description("Verify getAllBookingId returns 200 response and includes newly created booking in the list")
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
	@Description("Verify partialUpdateBooking service returns 200 response and updates the parameters provided in the request")
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
