package com.bekbuk.pethotelreservation.factory;

import com.bekbuk.pethotelreservation.entity.Booking;
import com.bekbuk.pethotelreservation.model.Request;
import com.bekbuk.pethotelreservation.model.Request.ReservationRequest;
import com.bekbuk.pethotelreservation.model.enums.CheckedStatus;
import com.bekbuk.pethotelreservation.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.Date;

public class EntityFactory {
    public static Booking getSampleBooking() {
        return Booking.builder()
                .checkinDate(new Date())
                .ownerName("Owner")
                .petName("Pet")
                .checkingStatus(CheckedStatus.NOT_CHECKED)
                .checkoutDate(new Date())
                .paymentStatus(PaymentStatus.NOT_PAID)
                .ownerPhone("12312312")
                .id(1L)
                .price(BigDecimal.TEN)
                .build();
    }

    public static ReservationRequest getSampleReservationRequest() {
        return Request.ReservationRequest.builder()
                .petName("Pet")
                .ownerName("Owner")
                .checkInDate(new Date())
                .checkOutDate(new Date())
                .ownerPhone("012312312")
                .build();
    }
}
