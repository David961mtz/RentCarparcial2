package edu.rentcar.payment;

import edu.rentcar.model.Customer;

public class PaymentRequest {
    private final String reservationId;
    private final Customer customer;
    private final double amount;
    private final String paymentToken;

    public PaymentRequest(String reservationId, Customer customer, double amount, String paymentToken) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.amount = amount;
        this.paymentToken = paymentToken;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentToken() {
        return paymentToken;
    }
}
