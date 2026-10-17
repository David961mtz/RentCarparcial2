
package edu.rentcar.app;

import com.rentcar.paymentclient.PaymentApiClient;
import edu.rentcar.facade.RentalFacade;
import edu.rentcar.payment.PaymentApiAdapter;
import edu.rentcar.payment.PaymentAuthorizationProxy;
import edu.rentcar.payment.PaymentGateway;
import edu.rentcar.pricing.PricingService;
import edu.rentcar.repository.CustomerRepository;
import edu.rentcar.repository.ReservationRepository;
import edu.rentcar.repository.VehicleRepository;
import edu.rentcar.service.AvailabilityService;
import edu.rentcar.service.CustomerService;
import edu.rentcar.service.VehicleService;
import edu.rentcar.verification.CustomerVerificationService;

public final class ApplicationServices {

    private static final VehicleService VEHICLE_SERVICE =
            new VehicleService(new VehicleRepository());

    // Se crea primero porque CustomerService lo necesita.
    private static final ReservationRepository RESERVATION_REPOSITORY =
            new ReservationRepository();

    private static final CustomerService CUSTOMER_SERVICE =
            new CustomerService(
                    new CustomerRepository(),
                    RESERVATION_REPOSITORY
            );

    private static final RentalFacade RENTAL_FACADE =
            createRentalFacade();

    private ApplicationServices() {
    }

    public static VehicleService vehicleService() {
        return VEHICLE_SERVICE;
    }

    public static CustomerService customerService() {
        return CUSTOMER_SERVICE;
    }

    public static ReservationRepository reservationRepository() {
        return RESERVATION_REPOSITORY;
    }

    public static RentalFacade rentalFacade() {
        return RENTAL_FACADE;
    }

    private static RentalFacade createRentalFacade() {
        PaymentGateway adapter =
                new PaymentApiAdapter(
                        new PaymentApiClient("http://localhost:8080")
                );

        PaymentGateway proxy =
                new PaymentAuthorizationProxy(
                        new CustomerVerificationService(),
                        adapter
                );

        return new RentalFacade(
                RESERVATION_REPOSITORY,
                new AvailabilityService(RESERVATION_REPOSITORY),
                new PricingService(),
                proxy
        );
    }
}
