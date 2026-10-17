package edu.rentcar.support;

import edu.rentcar.payment.PaymentGateway;
import edu.rentcar.payment.PaymentRequest;
import edu.rentcar.payment.PaymentResult;

public class FakePaymentGateway implements PaymentGateway {
    private int calls;
    private boolean approve = true;
    private String message = "APPROVED";

    public void setApprove(boolean approve) {
        this.approve = approve;
        this.message = approve ? "APPROVED" : "NOT_APPROVED";
    }

    public int getCalls() {
        return calls;
    }

    @Override
    public PaymentResult process(PaymentRequest request) {
        calls++;
        if (approve) {
            return PaymentResult.approved(message);
        }
        return PaymentResult.declined(message, true);
    }
}
