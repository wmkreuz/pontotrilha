package br.com.pontotrilha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pontotrilha.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}