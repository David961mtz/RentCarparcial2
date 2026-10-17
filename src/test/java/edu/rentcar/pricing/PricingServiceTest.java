package edu.rentcar.pricing;

import edu.rentcar.builder.RentalRequestBuilder;
import edu.rentcar.catalog.ServiceCatalog;
import edu.rentcar.data.SampleData;
import edu.rentcar.model.RentalRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PricingServiceTest {

    @Test
    void costIncludesModalityAndExtras() {
        RentalRequest request = new RentalRequestBuilder()
                .customer(SampleData.customers().get(0))
                .vehicle(SampleData.vehicles().get(0))
                .startDate(LocalDate.of(2026, 11, 1))
                .endDate(LocalDate.of(2026, 11, 4))
                .modality("Economy")
                .addExtra(ServiceCatalog.GPS)
                .build();

        RentalCost cost = new PricingService().calculate(request);

        assertEquals(3 * 150000 + 3 * 25000, cost.getCost());
        assertTrue(cost.getDescription().contains("GPS"));
        assertTrue(cost.getDescription().contains("Economy"));
    }

    @Test
    void premiumModalityAppliesMultiplier() {
        RentalRequest request = new RentalRequestBuilder()
                .customer(SampleData.customers().get(0))
                .vehicle(SampleData.vehicles().get(0))
                .startDate(LocalDate.of(2026, 11, 1))
                .endDate(LocalDate.of(2026, 11, 3))
                .modality("Premium")
                .build();

        RentalCost cost = new PricingService().calculate(request);

        assertEquals(2 * 150000 * 1.50, cost.getCost());
    }
}
