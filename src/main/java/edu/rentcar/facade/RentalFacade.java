
package edu.rentcar.facade;

import edu.rentcar.model.RentalRequest;
import edu.rentcar.model.Reservation;
import edu.rentcar.model.ReservationStatus;
import edu.rentcar.payment.PaymentGateway;
import edu.rentcar.payment.PaymentRequest;
import edu.rentcar.payment.PaymentResult;
import edu.rentcar.pricing.PricingService;
import edu.rentcar.pricing.RentalCost;
import edu.rentcar.repository.ReservationRepository;
import edu.rentcar.service.AvailabilityService;

import java.util.List;

public class RentalFacade {

    private final ReservationRepository reservationRepository;
    private final AvailabilityService availabilityService;
    private final PricingService pricingService;
    private final PaymentGateway paymentGateway;

    public RentalFacade(
            ReservationRepository reservationRepository,
            AvailabilityService availabilityService,
            PricingService pricingService,
            PaymentGateway paymentGateway
    ) {
        this.reservationRepository = reservationRepository;
        this.availabilityService = availabilityService;
        this.pricingService = pricingService;
        this.paymentGateway = paymentGateway;
    }

    public RentalProcessResult registerReservation(
            RentalRequest request,
            String paymentToken
    ) {
        String reservationId = reservationRepository.nextId();
        RentalCost cost = pricingService.calculate(request);

        if (!availabilityService.isAvailable(
                request.getVehicle(),
                request.getStartDate(),
                request.getEndDate()
        )) {
            return saveFailed(
                    reservationId,
                    request,
                    cost,
                    "Vehicle is not available for the selected dates."
            );
        }

        PaymentResult payment = paymentGateway.process(
                new PaymentRequest(
                        reservationId,
                        request.getCustomer(),
                        cost.getCost(),
                        paymentToken
                )
        );

        if (!payment.isApproved()) {
            return saveFailed(
                    reservationId,
                    request,
                    cost,
                    payment.getMessage()
            );
        }

        Reservation confirmed = new Reservation(
                reservationId,
                request.getCustomer(),
                request.getVehicle(),
                request.getStartDate(),
                request.getEndDate(),
                request.getModality(),
                cost.getDescription(),
                cost.getCost(),
                ReservationStatus.CONFIRMED,
                "Payment approved: " + payment.getMessage()
        );

        reservationRepository.save(confirmed);

        return new RentalProcessResult(
                true,
                "Reservation " + reservationId + " confirmed.",
                confirmed
        );
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public int getReservationCount() {
        return reservationRepository.count();
    }

    private RentalProcessResult saveFailed(
            String reservationId,
            RentalRequest request,
            RentalCost cost,
            String message
    ) {
        Reservation failed = new Reservation(
                reservationId,
                request.getCustomer(),
                request.getVehicle(),
                request.getStartDate(),
                request.getEndDate(),
                request.getModality(),
                cost.getDescription(),
                cost.getCost(),
                ReservationStatus.FAILED,
                message
        );

        reservationRepository.save(failed);

        return new RentalProcessResult(false, message, failed);
    }
}

