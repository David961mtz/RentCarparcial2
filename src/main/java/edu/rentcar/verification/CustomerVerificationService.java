package edu.rentcar.verification;

import edu.rentcar.model.Customer;

public class CustomerVerificationService {

    public VerificationResult verify(Customer customer) {
        if (customer == null || customer.getId() == null || customer.getId().isBlank()) {
            throw new IllegalStateException("Los datos del cliente están incompletos para verificarlo.");
        }
        if (customer.isAuthorized()) {
            return new VerificationResult(true, "El cliente está autorizado.");
        }
        return new VerificationResult(false, "El cliente no está autorizado para alquilar.");
    }
}
