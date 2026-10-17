package edu.rentcar.payment;

public class PaymentResult {
    private final boolean approved;
    private final String message;
    private final boolean attemptedExternalCall;

    private PaymentResult(boolean approved, String message, boolean attemptedExternalCall) {
        this.approved = approved;
        this.message = message;
        this.attemptedExternalCall = attemptedExternalCall;
    }

    public static PaymentResult approved(String message) {
        return new PaymentResult(true, message, true);
    }

    public static PaymentResult declined(String message, boolean attemptedExternalCall) {
        return new PaymentResult(false, message, attemptedExternalCall);
    }

    public static PaymentResult error(String message) {
        return new PaymentResult(false, message, true);
    }

    public boolean isApproved() {
        return approved;
    }

    public String getMessage() {
        return message;
    }

    public boolean attemptedExternalCall() {
        return attemptedExternalCall;
    }
}
