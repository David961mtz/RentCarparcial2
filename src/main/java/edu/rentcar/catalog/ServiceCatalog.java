package edu.rentcar.catalog;

public final class ServiceCatalog {
    public static final SimpleService GPS = new SimpleService("GPS", 25000);
    public static final SimpleService CHILD_SEAT = new SimpleService("Silla infantil", 18000);
    public static final SimpleService EXTRA_DRIVER = new SimpleService("Conductor extra", 45000);

    public static final ServicePackage TRAVEL_KIT = new ServicePackage("Kit de viaje")
            .add(GPS)
            .add(CHILD_SEAT);

    public static final ServicePackage FAMILY_PACK = new ServicePackage("Paquete familiar")
            .add(TRAVEL_KIT)
            .add(EXTRA_DRIVER);

    private ServiceCatalog() {
    }
}
