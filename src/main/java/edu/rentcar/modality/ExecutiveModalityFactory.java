package edu.rentcar.modality;

public class ExecutiveModalityFactory implements RentalModalityFactory {
    @Override
    public String name() {
        return "Executive";
    }

    @Override
    public RatePolicy createRatePolicy() {
        return new FixedRatePolicy(1.20);
    }

    @Override
    public CoveragePolicy createCoveragePolicy() {
        return new FixedCoveragePolicy("Standard collision and theft coverage");
    }

    @Override
    public AssistancePolicy createAssistancePolicy() {
        return new FixedAssistancePolicy("24-hour roadside assistance");
    }
}
