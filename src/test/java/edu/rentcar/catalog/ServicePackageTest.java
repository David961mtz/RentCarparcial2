package edu.rentcar.catalog;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServicePackageTest {

    @Test
    void packageCostIsSumOfServices() {
        assertEquals(43000, ServiceCatalog.TRAVEL_KIT.getDailyCost());
    }

    @Test
    void nestedPackageCostIsCorrect() {
        assertEquals(88000, ServiceCatalog.FAMILY_PACK.getDailyCost());
        assertTrueDescriptionContainsNestedItems();
    }

    private void assertTrueDescriptionContainsNestedItems() {
        String description = ServiceCatalog.FAMILY_PACK.getDescription();
        assertEquals(true, description.contains("Travel kit"));
        assertEquals(true, description.contains("Extra driver"));
    }
}
