package com.automationtest.assignment.domain.response;

import com.automationtest.assignment.domain.request.Bookingdates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingResponse {

	private String firstname;
	private String additionalneeds;
	private Bookingdates bookingdates;
	private Integer totalprice;
	private Boolean depositpaid;
	private String lastname;
}
