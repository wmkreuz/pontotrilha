package br.com.pontotrilha.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.model.Invoice;
import com.stripe.model.InvoiceLineItem;

import br.com.pontotrilha.model.Payments;
import br.com.pontotrilha.model.Ticket;
import br.com.pontotrilha.repositories.PaymentRepository;
import br.com.pontotrilha.repositories.UserRepository;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    public void registerNewPayment(Invoice invoice) {
        var newPayment = new Payments();

        var userFound = userRepository.findByUsername(invoice.getCustomerEmail());

        if (userFound == null) return;

        var user = userFound;

        newPayment.setCustomerId(invoice.getCustomer());
        newPayment.setEmail(invoice.getCustomerEmail());
        newPayment.setCardName(invoice.getCustomerName());
        newPayment.setAmountPaid(invoice.getAmountPaid());
        newPayment.setPaymentDate(LocalDateTime.now());
        newPayment.setPurchasedByUserId(user);

        paymentRepository.save(newPayment);

        List<InvoiceLineItem> lineItems = invoice.getLines().getData();

        /*Ticket ticket = new Ticket();
        ticket.setEventId(invoice.getprod);
        ticket.setPurchasedByUserId(user);*/

        for (InvoiceLineItem lineItem : lineItems) {
            // Acesse informações do produto associado a cada linha da fatura
            String productName = lineItem.getDescription(); // Isso pode variar dependendo de como os produtos são configurados

            // Faça algo com o nome do produto, como armazenar em um log, banco de dados, etc.
            System.out.println("Product Name: " + productName);
        }

    }
}