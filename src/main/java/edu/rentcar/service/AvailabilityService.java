package edu.rentcar.service;

import edu.rentcar.model.Reservation;
import edu.rentcar.model.ReservationStatus;
import edu.rentcar.model.Vehicle;
import edu.rentcar.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;

public class AvailabilityService {
    private final ReservationRepository reservationRepository;

    public AvailabilityService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public boolean isAvailable(Vehicle vehicle, LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
                continue;
            }
            if (!reservation.getVehicleId().equals(vehicle.getId())) {
                continue;
            }
            boolean overlaps = startDate.isBefore(reservation.getEndDate())
                    && endDate.isAfter(reservation.getStartDate());
            if (overlaps) {
                return false;
            }
        }
        return true;
    }
}
