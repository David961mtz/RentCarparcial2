package edu.rentcar.verification;

public class VerificationResult {
    private final boolean authorized;
    private final String message;

    public VerificationResult(boolean authorized, String message) {
        this.authorized = authorized;
        this.message = message;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public String getMessage() {
        return message;
    }
}
