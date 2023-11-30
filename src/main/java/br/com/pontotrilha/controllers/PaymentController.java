package br.com.pontotrilha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.ApiResource;
import com.stripe.param.checkout.SessionCreateParams;

import br.com.pontotrilha.mapper.DozerMapper;
import br.com.pontotrilha.model.Ticket;
import br.com.pontotrilha.repositories.TicketRepository;
import br.com.pontotrilha.repositories.UserRepository;
import br.com.pontotrilha.services.EventServices;
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

    @Autowired
	EventServices eventServices;

    @Autowired
	UserRepository userRepository;

    @Autowired
	TicketRepository ticketRepository;

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    @PostMapping("/webhook")
    public ResponseEntity webhook(@RequestBody String payload) {
        Event event = null;
       System.out.println(payload);

        try {
            event = ApiResource.GSON.fromJson(payload, Event.class);
            event.getId();
             System.out.println(event);
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
                // Then define and call a method to handle the successful attachment of a
                // PaymentMethod.
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

    @PostMapping("/checkout")
    public RedirectView checkout (@RequestParam Long eventId, @RequestParam Long quantity, @RequestParam String username) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        var event = eventServices.findById(eventId);

        var user = userRepository.findByUsername(username);

        Ticket ticket = new Ticket();
        var eventEntity = DozerMapper.parseObject(event, br.com.pontotrilha.model.Event.class);
        ticket.setEventId(eventEntity);
        ticket.setPurchasedByUserId(user);
        ticketRepository.save(ticket);

        String YOUR_DOMAIN = "https://pontotrilha.onrender.com/pagamento";

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(YOUR_DOMAIN + "?success=true")
                        .setCancelUrl(YOUR_DOMAIN + "?canceled=true")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice(event.getTickePriceStripe())
                                        .setQuantity(quantity)
                                        .build()
                        )
                        .setCustomerEmail(username)
                        .build();

        Session session = Session.create(params);

        String url = session.getUrl();
        return new RedirectView(url);
    }

}
