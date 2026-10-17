package edu.rentcar.builder;

import edu.rentcar.catalog.CatalogItem;
import edu.rentcar.model.Customer;
import edu.rentcar.model.RentalRequest;
import edu.rentcar.model.Vehicle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalRequestBuilder {
    private Customer customer;
    private Vehicle vehicle;
    private LocalDate startDate;
    private LocalDate endDate;
    private String modality;
    private final List<CatalogItem> extras = new ArrayList<>();

    public RentalRequestBuilder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public RentalRequestBuilder vehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public RentalRequestBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public RentalRequestBuilder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public RentalRequestBuilder modality(String modality) {
        this.modality = modality;
        return this;
    }

    public RentalRequestBuilder addExtra(CatalogItem extra) {
        if (extra != null) {
            extras.add(extra);
        }
        return this;
    }

    public RentalRequest build() {
        if (customer == null) {
            throw new IllegalArgumentException("El cliente es obligatorio.");
        }
        if (vehicle == null) {
            throw new IllegalArgumentException("El vehículo es obligatorio.");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("La fecha de inicio es obligatoria.");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("La fecha de fin es obligatoria.");
        }
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la de inicio.");
        }
        if (modality == null || modality.isBlank()) {
            throw new IllegalArgumentException("La modalidad es obligatoria.");
        }
        return new RentalRequest(customer, vehicle, startDate, endDate, modality.trim(), extras);
    }
}
