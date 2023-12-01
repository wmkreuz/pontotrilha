package br.com.pontotrilha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.pontotrilha.model.Payments;

public interface PaymentRepository extends JpaRepository<Payments, Long> {

    @Query("SELECT p FROM Payments p WHERE p.paymentIdStripe =:paymentIdStripe")
	Payments findBypaymentIdStripe(@Param("paymentIdStripe") String paymentIdStripe);
}