package edu.rentcar.facade;

import edu.rentcar.model.Reservation;

public class RentalProcessResult {
    private final boolean success;
    private final String message;
    private final Reservation reservation;

    public RentalProcessResult(boolean success, String message, Reservation reservation) {
        this.success = success;
        this.message = message;
        this.reservation = reservation;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
