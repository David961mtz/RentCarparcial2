package edu.rentcar.pricing;

public class BaseRentalCost implements RentalCost {
    private final double cost;
    private final String description;

    public BaseRentalCost(double cost, String description) {
        this.cost = cost;
        this.description = description;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
