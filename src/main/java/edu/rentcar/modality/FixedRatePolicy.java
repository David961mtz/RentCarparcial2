package edu.rentcar.modality;

public class FixedRatePolicy implements RatePolicy {
    private final double multiplier;

    public FixedRatePolicy(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public double apply(double baseDailyRate) {
        return baseDailyRate * multiplier;
    }
}
