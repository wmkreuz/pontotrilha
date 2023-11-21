package br.com.erudio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erudio.model.Map;

public interface MapRepository extends JpaRepository<Map, Long> {}