
package edu.rentcar.catalog;

public final class ServiceCatalog {

    public static final SimpleService GPS =
            new SimpleService("GPS", 25000);

    public static final SimpleService CHILD_SEAT =
            new SimpleService("Child seat", 18000);

    public static final SimpleService EXTRA_DRIVER =
            new SimpleService("Extra driver", 45000);

    public static final ServicePackage TRAVEL_KIT =
            new ServicePackage("Travel kit")
                    .add(GPS)
                    .add(CHILD_SEAT);

    public static final ServicePackage FAMILY_PACK =
            new ServicePackage("Family pack")
                    .add(TRAVEL_KIT)
                    .add(EXTRA_DRIVER);

    private ServiceCatalog() {
    }
}

