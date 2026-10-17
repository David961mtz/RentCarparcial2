package edu.rentcar.payment;

import edu.rentcar.verification.CustomerVerificationService;
import edu.rentcar.verification.VerificationResult;

public class PaymentAuthorizationProxy implements PaymentGateway {
    private final CustomerVerificationService verificationService;
    private final PaymentGateway realGateway;

    public PaymentAuthorizationProxy(CustomerVerificationService verificationService,
                                     PaymentGateway realGateway) {
        this.verificationService = verificationService;
        this.realGateway = realGateway;
    }

    @Override
    public PaymentResult process(PaymentRequest request) {
        try {
            VerificationResult verification = verificationService.verify(request.getCustomer());
            if (!verification.isAuthorized()) {
                return PaymentResult.declined(verification.getMessage(), false);
            }
            return realGateway.process(request);
        } catch (RuntimeException exception) {
            return PaymentResult.error("Error de verificación: " + exception.getMessage());
        }
    }
}
