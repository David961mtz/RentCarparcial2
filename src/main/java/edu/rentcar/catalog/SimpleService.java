package edu.rentcar.catalog;

public class SimpleService implements CatalogItem {
    private final String name;
    private final double dailyCost;

    public SimpleService(String name, double dailyCost) {
        this.name = name;
        this.dailyCost = dailyCost;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getDailyCost() {
        return dailyCost;
    }

    @Override
    public String getDescription() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (" + (int) dailyCost + " / day)";
    }
}
