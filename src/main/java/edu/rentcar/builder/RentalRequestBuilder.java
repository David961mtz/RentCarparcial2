
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
            throw new IllegalArgumentException("Customer is required.");
        }

        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle is required.");
        }

        if (startDate == null) {
            throw new IllegalArgumentException("Start date is required.");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("End date is required.");
        }

        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException(
                    "End date must be after start date."
            );
        }

        if (modality == null || modality.isBlank()) {
            throw new IllegalArgumentException("Modality is required.");
        }

        return new RentalRequest(
                customer,
                vehicle,
                startDate,
                endDate,
                modality.trim(),
                extras
        );
    }
}
