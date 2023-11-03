package br.com.empresa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.empresa.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}