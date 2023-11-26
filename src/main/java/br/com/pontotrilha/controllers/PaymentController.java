package br.com.pontotrilha.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.Invoice;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.StripeObject;
import com.stripe.net.ApiResource;
import com.stripe.param.PaymentIntentCreateParams;

import br.com.pontotrilha.services.PaymentService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payments")
@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/api/payment/v1")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    @CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
    @GetMapping("/newPayment")
    public ResponseEntity newPayment() throws StripeException {
        Stripe.apiKey = stripeApiKey;

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(1000L)
                .setCurrency("brl")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods
                                .builder()
                                .setEnabled(true)
                                .build()
                )
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        Map<String, String> map = new HashMap<>();
        map.put("client_secret", paymentIntent.getClientSecret());

        return ResponseEntity.ok().body(map);
    }

    @CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
    @PostMapping("/webhook")
    public ResponseEntity webhook(@RequestBody String payload) {
        Event event = null;

        try {
            event = ApiResource.GSON.fromJson(payload, Event.class);
        } catch (JsonSyntaxException e) {
            System.out.println("Webhook error while parsing basic request.");
            return ResponseEntity.badRequest().body("Webhook error while parsing basic request.");
        }

        // Deserializae the nested object inside the event
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
            // instructions on how to handle this case, or return an error here.
        }

        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                System.out.println("Payment for " + paymentIntent.getAmount() + " succeeded");
                // Then define and call a method to handle the successful payment intent.
                // handlePaymentIntentSucceeded(paymentIntent);
                break;
            case "payment_method.attached":
                PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
                // Then define and call a method to handle the successful attachment of a PaymentMethod.
                // handlePaymentMethodAttached(paymentMethod);
                break;
            case "customer.subscription.created":
                System.out.println("Subscription created");
                break;
            case "customer.subscription.updated":
                System.out.println("Subscription updated");
                break;
            case "customer.subscription.deleted":
                System.out.println("Subscription deleted");
                break;
            case "invoice.payment_succeeded":
                Invoice invoice = (Invoice) stripeObject;
                service.registerNewPayment(invoice);
                System.out.println("Payment successfully made by the user " + invoice.getCustomerEmail());
                break;
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }

        return ResponseEntity.ok().body("Payment Received successfully!");
    }
}
