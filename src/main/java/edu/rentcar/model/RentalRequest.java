package edu.rentcar.model;

import edu.rentcar.catalog.CatalogItem;

import java.time.LocalDate;
import java.util.List;

public class RentalRequest {
    private final Customer customer;
    private final Vehicle vehicle;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String modality;
    private final List<CatalogItem> extras;

    public RentalRequest(Customer customer, Vehicle vehicle, LocalDate startDate,
                         LocalDate endDate, String modality, List<CatalogItem> extras) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.modality = modality;
        this.extras = List.copyOf(extras);
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
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

    public List<CatalogItem> getExtras() {
        return extras;
    }
}
