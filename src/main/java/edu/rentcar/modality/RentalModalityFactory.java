package edu.rentcar.modality;

public interface RentalModalityFactory {
    String name();

    default String displayName() {
        return switch (name()) {
            case "Economy" -> "Económica";
            case "Executive" -> "Ejecutiva";
            case "Premium" -> "Premium";
            default -> name();
        };
    }

    RatePolicy createRatePolicy();

    CoveragePolicy createCoveragePolicy();

    AssistancePolicy createAssistancePolicy();
}
