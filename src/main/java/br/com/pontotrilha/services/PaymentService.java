package br.com.pontotrilha.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontotrilha.model.Payments;
import br.com.pontotrilha.repositories.PaymentRepository;
import br.com.pontotrilha.repositories.UserRepository;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    public void registerNewPayment(Payments payment) {
        paymentRepository.save(payment);
    }
}