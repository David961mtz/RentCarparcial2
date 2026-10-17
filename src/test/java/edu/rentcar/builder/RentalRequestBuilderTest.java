package edu.rentcar.builder;

import edu.rentcar.catalog.ServiceCatalog;
import edu.rentcar.data.SampleData;
import edu.rentcar.model.RentalRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RentalRequestBuilderTest {

    @Test
    void buildValidRequest() {
        RentalRequest request = validBuilder().addExtra(ServiceCatalog.GPS).build();

        assertEquals("CUS-1001", request.getCustomer().getId());
        assertEquals("VEH-1001", request.getVehicle().getId());
        assertEquals("Economy", request.getModality());
        assertEquals(1, request.getExtras().size());
    }

    @Test
    void rejectMissingCustomer() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validBuilder().customer(null).build()
        );
        assertEquals("Customer is required.", exception.getMessage());
    }

    @Test
    void rejectInvalidDates() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validBuilder()
                        .startDate(LocalDate.of(2026, 11, 10))
                        .endDate(LocalDate.of(2026, 11, 10))
                        .build()
        );
        assertEquals("End date must be after start date.", exception.getMessage());
    }

    @Test
    void rejectMissingModality() {
        assertThrows(IllegalArgumentException.class, () -> validBuilder().modality(" ").build());
    }

    private RentalRequestBuilder validBuilder() {
        return new RentalRequestBuilder()
                .customer(SampleData.customers().get(0))
                .vehicle(SampleData.vehicles().get(0))
                .startDate(LocalDate.of(2026, 11, 1))
                .endDate(LocalDate.of(2026, 11, 4))
                .modality("Economy");
    }
}
