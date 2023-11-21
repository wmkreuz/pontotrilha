package br.com.erudio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erudio.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {}