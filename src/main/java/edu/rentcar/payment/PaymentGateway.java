package edu.rentcar.payment;

public interface PaymentGateway {
    PaymentResult process(PaymentRequest request);
}
