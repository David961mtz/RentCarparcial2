package edu.rentcar.catalog;

import java.util.ArrayList;
import java.util.List;

public class ServicePackage implements CatalogItem {
    private final String name;
    private final List<CatalogItem> items = new ArrayList<>();

    public ServicePackage(String name) {
        this.name = name;
    }

    public ServicePackage add(CatalogItem item) {
        items.add(item);
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getDailyCost() {
        double total = 0;
        for (CatalogItem item : items) {
            total += item.getDailyCost();
        }
        return total;
    }

    @Override
    public String getDescription() {
        StringBuilder builder = new StringBuilder(name).append(" [");
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(items.get(i).getDescription());
        }
        return builder.append("]").toString();
    }

    @Override
    public String toString() {
        return name + " (" + (int) getDailyCost() + " / day)";
    }
}
