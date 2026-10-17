package edu.rentcar.modality;

public final class ModalityFactoryProvider {
    private ModalityFactoryProvider() {
    }

    public static RentalModalityFactory getFactory(String modality) {
        if (modality == null) {
            throw new IllegalArgumentException("Modality is required.");
        }
        return switch (modality) {
            case "Economy" -> new EconomyModalityFactory();
            case "Executive" -> new ExecutiveModalityFactory();
            case "Premium" -> new PremiumModalityFactory();
            default -> throw new IllegalArgumentException("Unknown modality: " + modality);
        };
    }
}
