package com.bekbuk.pethotelreservation.service;

import com.bekbuk.pethotelreservation.entity.Booking;
import com.bekbuk.pethotelreservation.exception.PetHotelReservationException;
import com.bekbuk.pethotelreservation.factory.EntityFactory;
import com.bekbuk.pethotelreservation.model.Request;
import com.bekbuk.pethotelreservation.model.enums.PaymentStatus;
import com.bekbuk.pethotelreservation.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @InjectMocks
    ReservationService reservationService;

    @Mock
    ReservationRepository reservationRepository;

    @Test
    public void whenCreatReservation_withValidValues_thenReturnSuccess() {
        Request.ReservationRequest request = EntityFactory.getSampleReservationRequest();
        reservationService.createReservation(request);

        verify(reservationRepository, times(1)).save(any());
    }

    @Test
    public void whenGetReservationByPetName_withValidValues_thenReturnBooking() {
        String petName = "Pet";
        Booking booking = EntityFactory.getSampleBooking();
        given(reservationRepository.findByPetName(petName)).willReturn(Optional.of(booking));

        Booking reservationByPetName = reservationService.getReservationByPetName(petName);

        assertThat(reservationByPetName).isEqualTo(booking);
    }

    @Test
    public void whenGetReservationByPetName_withInvalidValues_thenThrowException() {
        String petName = "Pet";
        given(reservationRepository.findByPetName(petName)).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> reservationService.getReservationByPetName(petName));

        assertThat(throwable).isInstanceOf(PetHotelReservationException.class);
    }

    @Test
    public void whenPay_thenUpdatePaymentStatus() {
        String petName = "Pet";
        Booking booking = EntityFactory.getSampleBooking();
        booking.setPaymentStatus(PaymentStatus.NOT_PAID);
        booking.setPetName(petName);

        given(reservationRepository.findByPetName(petName)).willReturn(Optional.of(booking));

        reservationService.pay(petName);

        ArgumentCaptor<Booking> argumentCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(reservationRepository, times(1)).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
    }
}