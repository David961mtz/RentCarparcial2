package edu.rentcar.modality;

public class PremiumModalityFactory implements RentalModalityFactory {
    @Override
    public String name() {
        return "Premium";
    }

    @Override
    public RatePolicy createRatePolicy() {
        return new FixedRatePolicy(1.50);
    }

    @Override
    public CoveragePolicy createCoveragePolicy() {
        return new FixedCoveragePolicy("Full coverage with zero deductible");
    }

    @Override
    public AssistancePolicy createAssistancePolicy() {
        return new FixedAssistancePolicy("Premium assistance with replacement vehicle");
    }
}
