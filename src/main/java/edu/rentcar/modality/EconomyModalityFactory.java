package edu.rentcar.modality;

public class EconomyModalityFactory implements RentalModalityFactory {
    @Override
    public String name() {
        return "Economy";
    }

    @Override
    public RatePolicy createRatePolicy() {
        return new FixedRatePolicy(1.0);
    }

    @Override
    public CoveragePolicy createCoveragePolicy() {
        return new FixedCoveragePolicy("Basic liability coverage");
    }

    @Override
    public AssistancePolicy createAssistancePolicy() {
        return new FixedAssistancePolicy("Standard roadside assistance");
    }
}
