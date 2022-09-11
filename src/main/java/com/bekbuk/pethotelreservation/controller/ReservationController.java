package com.bekbuk.pethotelreservation.controller;

import com.bekbuk.pethotelreservation.model.Request.ReservationRequest;
import com.bekbuk.pethotelreservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public void createReservation(@RequestBody ReservationRequest request) {
        reservationService.createReservation(request);
    }

    @PostMapping("/check-in")
    public String checkin(@RequestParam String petName){
        return reservationService.checkin(petName);
    }

    @PostMapping("/pay")
    public void pay(@RequestParam String petName) throws Exception {
        reservationService.pay(petName);
    }

}
