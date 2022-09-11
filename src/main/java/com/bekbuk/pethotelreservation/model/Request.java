package com.bekbuk.pethotelreservation.model;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Request {
    public record ReservationRequest(
            @NotBlank
            String petName,
            @NotNull
            Date checkInDate,
            @NotNull
            Date checkOutDate,
            @NotNull
            String ownerName,
            @NotNull
            @NumberFormat
            String ownerPhone
    ) {
    }
}
