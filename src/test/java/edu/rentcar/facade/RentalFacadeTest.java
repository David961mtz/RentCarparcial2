package edu.rentcar.facade;

import edu.rentcar.builder.RentalRequestBuilder;
import edu.rentcar.catalog.ServiceCatalog;
import edu.rentcar.data.SampleData;
import edu.rentcar.model.ReservationStatus;
import edu.rentcar.payment.PaymentAuthorizationProxy;
import edu.rentcar.pricing.PricingService;
import edu.rentcar.repository.ReservationRepository;
import edu.rentcar.service.AvailabilityService;
import edu.rentcar.support.FakePaymentGateway;
import edu.rentcar.verification.CustomerVerificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RentalFacadeTest {
    private ReservationRepository reservationRepository;
    private FakePaymentGateway paymentGateway;
    private RentalFacade facade;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository();
        paymentGateway = new FakePaymentGateway();
        facade = new RentalFacade(
                reservationRepository,
                new AvailabilityService(reservationRepository),
                new PricingService(),
                new PaymentAuthorizationProxy(new CustomerVerificationService(), paymentGateway)
        );
    }

    @Test
    void successfulRentalIsConfirmed() {
        RentalProcessResult result = facade.registerReservation(
                validRequest().addExtra(ServiceCatalog.GPS).build(),
                "tok_approved"
        );

        assertTrue(result.isSuccess());
        assertEquals(ReservationStatus.CONFIRMED, result.getReservation().getStatus());
        assertEquals(1, paymentGateway.getCalls());
        assertEquals(1, facade.getReservationCount());
        assertTrue(result.getReservation().getTotalCost() > 0);
    }

    @Test
    void failedRentalWhenPaymentIsDeclined() {
        paymentGateway.setApprove(false);

        RentalProcessResult result = facade.registerReservation(validRequest().build(), "tok_declined");

        assertFalse(result.isSuccess());
        assertEquals(ReservationStatus.FAILED, result.getReservation().getStatus());
        assertEquals(1, paymentGateway.getCalls());
    }

    @Test
    void failedRentalWhenVehicleIsNotAvailable() {
        facade.registerReservation(validRequest().build(), "tok_approved");
        RentalProcessResult second = facade.registerReservation(validRequest().build(), "tok_approved");

        assertFalse(second.isSuccess());
        assertEquals("Vehicle is not available for the selected dates.", second.getMessage());
        assertEquals(1, paymentGateway.getCalls());
        assertEquals(ReservationStatus.FAILED, second.getReservation().getStatus());
    }

    @Test
    void failedRentalWhenCustomerIsNotAuthorized() {
        RentalProcessResult result = facade.registerReservation(
                validRequest().customer(SampleData.customers().get(2)).build(),
                "tok_approved"
        );

        assertFalse(result.isSuccess());
        assertEquals(0, paymentGateway.getCalls());
        assertEquals(ReservationStatus.FAILED, result.getReservation().getStatus());
    }

    private RentalRequestBuilder validRequest() {
        return new RentalRequestBuilder()
                .customer(SampleData.customers().get(0))
                .vehicle(SampleData.vehicles().get(0))
                .startDate(LocalDate.of(2026, 11, 1))
                .endDate(LocalDate.of(2026, 11, 4))
                .modality("Economy");
    }
}
