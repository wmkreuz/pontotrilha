package br.com.pontotrilha.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventCollection;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;

import br.com.pontotrilha.model.Payments;
import br.com.pontotrilha.repositories.PaymentRepository;
import br.com.pontotrilha.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeEventJobService {

    @Autowired
    private PaymentService service;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    @Scheduled(fixedRate = 20000)
    public void consultarEventosStripe() {
        try {
            Stripe.apiKey = stripeApiKey;

            Map<String, Object> params = new HashMap<>();
            params.put("limit", 100);

            EventCollection eventCollection = Event.list(params);

            for (Event event : eventCollection.getData()) {

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
                    case "checkout.session.completed":

                        Session session = (Session) stripeObject;

                        if (session != null) {

                            var user = userRepository.findByUsername(session.getCustomerDetails().getEmail());

                            if (paymentRepository
                                    .findBypaymentIdStripe(session.getId()) == null) {

                                Payments newPayment = new Payments();

                                newPayment.setAmountPaid(session.getAmountTotal());
                                newPayment.setPaymentDate(LocalDateTime.now());
                                newPayment.setPaymentIdStripe(session.getId());
                                newPayment.setPurchasedByUserId(user);

                                service.registerNewPayment(newPayment);

                                System.out.println("Payment successfully made by the user "
                                        + session.getCustomerDetails().getEmail());
                            }

                        }

                        break;
                    default:
                        System.out.println("Unhandled event type: " + event.getType());
                }
            }
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}