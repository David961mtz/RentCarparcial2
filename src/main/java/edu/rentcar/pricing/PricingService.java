
package edu.rentcar.pricing;

import edu.rentcar.catalog.CatalogItem;
import edu.rentcar.modality.ModalityFactoryProvider;
import edu.rentcar.modality.RentalModalityFactory;
import edu.rentcar.model.RentalRequest;

import java.time.temporal.ChronoUnit;

public class PricingService {

    public long rentalDays(RentalRequest request) {
        long days = ChronoUnit.DAYS.between(
                request.getStartDate(),
                request.getEndDate()
        );

        return Math.max(days, 1);
    }

    public RentalCost calculate(RentalRequest request) {
        RentalModalityFactory factory =
                ModalityFactoryProvider.getFactory(request.getModality());

        long days = rentalDays(request);

        double dailyRate = factory.createRatePolicy()
                .apply(request.getVehicle().getDailyRate());

        String description = days + " day(s) " + factory.name()
                + " | " + factory.createCoveragePolicy().description()
                + " | " + factory.createAssistancePolicy().description();

        RentalCost cost = new BaseRentalCost(
                dailyRate * days,
                description
        );

        for (CatalogItem extra : request.getExtras()) {
            cost = new ExtraServiceDecorator(
                    cost,
                    extra.getDescription(),
                    extra.getDailyCost() * days
            );
        }

        return cost;
    }
}
