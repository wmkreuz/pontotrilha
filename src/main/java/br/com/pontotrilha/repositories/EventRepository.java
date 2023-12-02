package br.com.pontotrilha.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.pontotrilha.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.createdByUser.userName =:username")
	List<Event> findAllUserEventsByUsername(@Param("username") String username);

}