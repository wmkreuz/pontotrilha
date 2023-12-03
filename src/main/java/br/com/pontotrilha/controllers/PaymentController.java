package br.com.pontotrilha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import br.com.pontotrilha.model.Event;
import br.com.pontotrilha.model.User;
import br.com.pontotrilha.repositories.TicketRepository;
import br.com.pontotrilha.repositories.UserRepository;
import br.com.pontotrilha.services.EventServices;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payments")
@RestController
@RequestMapping("/api/payment/v1")
public class PaymentController {

    @Autowired
    EventServices eventServices;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    @CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
    @PostMapping("/checkout")
    public String checkout(Event event, User user, Long quantity)
            throws StripeException {

        Stripe.apiKey = stripeApiKey;

        String YOUR_DOMAIN = "https://pontotrilha.onrender.com/pagamento";

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(YOUR_DOMAIN + "?success=true")
                .setCancelUrl(YOUR_DOMAIN + "?canceled=true")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPrice(event.getTickePriceStripe())
                                .setQuantity(quantity)
                                .build())
                .setCustomerEmail(user.getUserName())
                .build();

        Session session = Session.create(params);

        String url = session.getUrl();
        return url;
    }
}