package com.bekbuk.pethotelreservation.service;

import com.bekbuk.pethotelreservation.entity.Booking;
import com.bekbuk.pethotelreservation.exception.PetHotelReservationException;
import com.bekbuk.pethotelreservation.model.Request;
import com.bekbuk.pethotelreservation.model.enums.CheckedStatus;
import com.bekbuk.pethotelreservation.model.enums.PaymentStatus;
import com.bekbuk.pethotelreservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.bekbuk.pethotelreservation.constants.ConstantsMessages.*;
import static com.bekbuk.pethotelreservation.model.enums.CheckedStatus.CHECKED;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private static final BigDecimal DAILY_PRICE = BigDecimal.TEN;

    private final ReservationRepository reservationRepository;

    @Transactional
    public void createReservation(Request.ReservationRequest request) {
        Booking booking = Booking.builder()
                .petName(request.petName())
                .checkinDate(request.checkInDate())
                .checkoutDate(request.checkOutDate())
                .ownerName(request.ownerName())
                .paymentStatus(PaymentStatus.NOT_PAID)
                .checkingStatus(CheckedStatus.NOT_CHECKED)
                .ownerPhone(request.ownerPhone())
                .price(calculatePrice(request.checkInDate(), request.checkOutDate()))
                .build();

        reservationRepository.save(booking);
    }

    public Booking getReservationByPetName(String petName) {
        return reservationRepository.findByPetName(petName)
                .orElseThrow(() -> new PetHotelReservationException(REZERVATION_NOT_FOUND));
    }

    @Transactional
    public void checkin(String petName) {
        Booking booking = getReservationByPetName(petName);

        if (booking.getCheckinDate().before(new Date()))
            throw new PetHotelReservationException(CHECKIN_DATE_BEFORE);
        if (!PaymentStatus.PAID.equals(booking.getPaymentStatus()))
            throw new PetHotelReservationException(PAYMENT_REQUIRED);

        booking.setCheckingStatus(CHECKED);
        reservationRepository.save(booking);
    }

    @Transactional
    public void pay(String petName) {
        Booking booking = getReservationByPetName(petName);
        booking.setPaymentStatus(PaymentStatus.PAID);
        reservationRepository.save(booking);
    }

    private BigDecimal calculatePrice(Date in, Date out) {
        long diffInMillies = Math.abs(out.getTime() - in.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return DAILY_PRICE.multiply(BigDecimal.valueOf(diff));
    }
}
