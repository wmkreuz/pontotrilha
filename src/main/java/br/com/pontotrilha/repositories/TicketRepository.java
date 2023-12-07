package br.com.pontotrilha.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.pontotrilha.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.purchasedByUser.userName =:username")
	List<Ticket> findAllUserTicketsByUsername(@Param("username") String username);

}