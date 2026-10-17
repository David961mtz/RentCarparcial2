package edu.rentcar.repository;

import edu.rentcar.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();
    private int nextId = 1001;

    public String nextId() {
        return "RES-" + nextId++;
    }

    public Reservation save(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    public int count() {
        return reservations.size();
    }

    public boolean hasReservationsForCustomer(String customerId) {
        return reservations.stream()
                .anyMatch(reservation ->
                        reservation.getCustomerId().equals(customerId));
    }
}
