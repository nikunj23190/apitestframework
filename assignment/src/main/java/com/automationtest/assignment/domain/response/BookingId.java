package com.automationtest.assignment.domain.response;

import com.automationtest.assignment.domain.request.Booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingId {
	private Integer bookingid;
}

