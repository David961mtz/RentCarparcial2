package edu.rentcar.pricing;

public class ExtraServiceDecorator implements RentalCost {
    private final RentalCost inner;
    private final String extraName;
    private final double extraCost;

    public ExtraServiceDecorator(RentalCost inner, String extraName, double extraCost) {
        this.inner = inner;
        this.extraName = extraName;
        this.extraCost = extraCost;
    }

    @Override
    public double getCost() {
        return inner.getCost() + extraCost;
    }

    @Override
    public String getDescription() {
        return inner.getDescription() + " + " + extraName;
    }
}
