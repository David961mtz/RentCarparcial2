package edu.rentcar.payment;

import com.rentcar.paymentclient.PaymentApiClient;
import com.rentcar.paymentclient.PaymentApiRequest;
import com.rentcar.paymentclient.PaymentApiResponse;

import java.io.IOException;

public class PaymentApiAdapter implements PaymentGateway {
    private final PaymentApiClient client;

    public PaymentApiAdapter(PaymentApiClient client) {
        this.client = client;
    }

    @Override
    public PaymentResult process(PaymentRequest request) {
        PaymentApiRequest apiRequest = new PaymentApiRequest(
                request.getReservationId(),
                request.getCustomer().getId(),
                request.getAmount(),
                request.getPaymentToken()
        );

        try {
            PaymentApiResponse response = client.pay(apiRequest);
            if (response.isApproved()) {
                return PaymentResult.approved(response.body());
            }
            if (response.isDeclined()) {
                return PaymentResult.declined(response.body(), true);
            }
            return PaymentResult.error("Error de la API de pagos HTTP " + response.httpStatus() + ": " + response.body());
        } catch (IOException exception) {
            String detail = exception.getMessage();
            if (detail == null || detail.isBlank()) {
                detail = "no hay conexión. Inicie la API con: java -jar external/payment-api.jar";
            }
            return PaymentResult.error("Error de conexión con la API de pagos: " + detail);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return PaymentResult.error("El pago fue interrumpido.");
        }
    }
}
