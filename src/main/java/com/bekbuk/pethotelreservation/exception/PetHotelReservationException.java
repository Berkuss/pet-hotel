package com.bekbuk.pethotelreservation.exception;

public class PetHotelReservationException extends RuntimeException {
    private final String failedReason;

    public PetHotelReservationException(String failedReason) {
        super(failedReason);
        this.failedReason = failedReason;
    }

    public String getFailedReason() {
        return failedReason;
    }
}
