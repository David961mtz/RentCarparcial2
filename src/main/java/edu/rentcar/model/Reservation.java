package edu.rentcar.model;

import java.time.LocalDate;

public class Reservation {
    private final String id;
    private final Customer customer;
    private final Vehicle vehicle;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String modality;
    private final String extrasDescription;
    private final double totalCost;
    private final ReservationStatus status;
    private final String message;

    public Reservation(String id, Customer customer, Vehicle vehicle,
                       LocalDate startDate, LocalDate endDate, String modality,
                       String extrasDescription, double totalCost,
                       ReservationStatus status, String message) {
        this.id = id;
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.modality = modality;
        this.extrasDescription = extrasDescription;
        this.totalCost = totalCost;
        this.status = status;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCustomerId() {
        return customer.getId();
    }

    public String getCustomerName() {
        return customer.getFullName();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getVehicleId() {
        return vehicle.getId();
    }

    public String getVehicleName() {
        return vehicle.getDisplayName();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getModality() {
        return modality;
    }

    public String getExtrasDescription() {
        return extrasDescription;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public String getStatusLabel() {
        return status == ReservationStatus.CONFIRMED ? "Confirmada" : "Fallida";
    }

    public String getModalityLabel() {
        return switch (modality) {
            case "Economy" -> "Económica";
            case "Executive" -> "Ejecutiva";
            case "Premium" -> "Premium";
            default -> modality;
        };
    }

    public String getMessage() {
        return message;
    }
}
