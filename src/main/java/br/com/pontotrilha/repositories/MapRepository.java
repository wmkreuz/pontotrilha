package br.com.pontotrilha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pontotrilha.model.Map;

public interface MapRepository extends JpaRepository<Map, Long> {}