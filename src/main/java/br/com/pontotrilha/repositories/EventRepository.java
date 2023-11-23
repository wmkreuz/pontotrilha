package br.com.pontotrilha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pontotrilha.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {}