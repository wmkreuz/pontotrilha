package br.com.pontotrilha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pontotrilha.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {}