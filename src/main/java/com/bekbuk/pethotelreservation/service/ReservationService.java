package com.bekbuk.pethotelreservation.service;

import com.bekbuk.pethotelreservation.entity.Booking;
import com.bekbuk.pethotelreservation.model.Request;
import com.bekbuk.pethotelreservation.model.enums.CheckedStatus;
import com.bekbuk.pethotelreservation.model.enums.PaymentStatus;
import com.bekbuk.pethotelreservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

    private BigDecimal calculatePrice(Date in, Date out) {
        long diffInMillies = Math.abs(out.getTime() - in.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return DAILY_PRICE.multiply(BigDecimal.valueOf(diff));
    }

    public String checkin(String petName) {
        Optional<Booking> optionalBooking = reservationRepository.findByPetName(petName);
        if (optionalBooking.isEmpty()) return "Reservation not Found";

        Booking booking = optionalBooking.get();
        if (booking.getPaymentStatus().equals(PaymentStatus.PAID)) {
            booking.setCheckingStatus(CHECKED);
            reservationRepository.save(booking);
            return "success";
        }

        return "Not paid";
    }

    @Transactional
    public void pay(String petName) throws Exception {
        Optional<Booking> optionalBooking = reservationRepository.findByPetName(petName);
        if (optionalBooking.isEmpty()) throw new Exception("Reservation not Found");
        Booking booking = optionalBooking.get();
        booking.setPaymentStatus(PaymentStatus.PAID);
        reservationRepository.save(booking);
    }
}
