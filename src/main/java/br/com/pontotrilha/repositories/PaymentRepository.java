package br.com.pontotrilha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pontotrilha.model.Payments;

public interface PaymentRepository extends JpaRepository<Payments, Long> {}