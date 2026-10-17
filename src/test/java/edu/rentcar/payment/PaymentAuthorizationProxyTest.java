package edu.rentcar.payment;

import edu.rentcar.data.SampleData;
import edu.rentcar.support.FakePaymentGateway;
import edu.rentcar.verification.CustomerVerificationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentAuthorizationProxyTest {

    @Test
    void authorizedCustomerCallsPaymentGateway() {
        FakePaymentGateway gateway = new FakePaymentGateway();
        PaymentAuthorizationProxy proxy = new PaymentAuthorizationProxy(
                new CustomerVerificationService(), gateway);

        PaymentResult result = proxy.process(new PaymentRequest(
                "RES-1", SampleData.customers().get(0), 100000, "tok_approved"));

        assertTrue(result.isApproved());
        assertEquals(1, gateway.getCalls());
        assertTrue(result.attemptedExternalCall());
    }

    @Test
    void unauthorizedCustomerDoesNotCallPaymentGateway() {
        FakePaymentGateway gateway = new FakePaymentGateway();
        PaymentAuthorizationProxy proxy = new PaymentAuthorizationProxy(
                new CustomerVerificationService(), gateway);

        PaymentResult result = proxy.process(new PaymentRequest(
                "RES-1", SampleData.customers().get(2), 100000, "tok_approved"));

        assertFalse(result.isApproved());
        assertEquals(0, gateway.getCalls());
        assertFalse(result.attemptedExternalCall());
    }
}
