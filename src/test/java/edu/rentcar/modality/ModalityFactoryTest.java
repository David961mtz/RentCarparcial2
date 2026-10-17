package edu.rentcar.modality;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ModalityFactoryTest {

    @Test
    void factoriesCreateDifferentPolicies() {
        RentalModalityFactory economy = new EconomyModalityFactory();
        RentalModalityFactory premium = new PremiumModalityFactory();

        assertEquals(150000, economy.createRatePolicy().apply(150000));
        assertEquals(225000, premium.createRatePolicy().apply(150000));
        assertNotEquals(economy.createCoveragePolicy().description(),
                premium.createCoveragePolicy().description());
    }

    @Test
    void providerReturnsRequestedFactory() {
        assertEquals("Executive", ModalityFactoryProvider.getFactory("Executive").name());
    }
}
