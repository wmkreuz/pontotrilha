package br.com.empresa.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.empresa.data.vo.v1.BookVO;
import br.com.empresa.exceptions.RequiredObjectIsNullException;
import br.com.empresa.model.Book;
import br.com.empresa.repositories.BookRepository;
import br.com.empresa.services.BookServices;
import br.com.empresa.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServicesTest {
    
    MockBook input;

	@InjectMocks
	private BookServices service;

	@Mock
	BookRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

    @Test
    void testFindById() {

        Book entity = input.mockEntity(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Author Test1", result.getAuthor());
		assertEquals("Title Test1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());

    }
    
    @Test
    void testCreate() {

        Book entity = input.mockEntity(1);

		Book persisted = entity;
		persisted.setId(1L);

		BookVO vo = input.mockVO(1);
		vo.setKey(1L);

		when(repository.save(entity)).thenReturn(persisted);

		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Author Test1", result.getAuthor());
		assertEquals("Title Test1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());

    }

    @Test
	void testCreateWithNullPerson() {

		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});

		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		
	}

    @Test
    void testUpdate() {

        Book entity = input.mockEntity(1);
		entity.setId(1L);

		Book persisted = entity;
		persisted.setId(1L);

		BookVO vo = input.mockVO(1);
		vo.setKey(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);

		var result = service.update(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Author Test1", result.getAuthor());
		assertEquals("Title Test1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());

    }

    @Test
	void testUpdateWithNullPerson() {

		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});

		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		
	}

    @Test
    void testDelete() {

        Book entity = input.mockEntity(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		service.delete(1L);

    }

    @Test
    void testFindAll() {

		List<Book> list = input.mockEntityList();

		when(repository.findAll()).thenReturn(list);

		var books = service.findAll();
		assertNotNull(books);
		assertEquals(14, books.size());

		var BookOne = books.get(1);
		assertNotNull(BookOne);
		assertNotNull(BookOne.getKey());
		assertNotNull(BookOne.getLinks());

		assertTrue(BookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Author Test1", BookOne.getAuthor());
		assertEquals("Title Test1", BookOne.getTitle());
		assertEquals(25D, BookOne.getPrice());
		assertNotNull(BookOne.getLaunchDate());

		var BookFour = books.get(4);
		assertNotNull(BookFour);
		assertNotNull(BookFour.getKey());
		assertNotNull(BookFour.getLinks());

		assertTrue(BookFour.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
		assertEquals("Author Test4", BookFour.getAuthor());
		assertEquals("Title Test4", BookFour.getTitle());
		assertEquals(25D, BookFour.getPrice());
		assertNotNull(BookFour.getLaunchDate());

		var BookSeven = books.get(7);
		assertNotNull(BookSeven);
		assertNotNull(BookSeven.getKey());
		assertNotNull(BookSeven.getLinks());

		assertTrue(BookSeven.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));
		assertEquals("Author Test7", BookSeven.getAuthor());
		assertEquals("Title Test7", BookSeven.getTitle());
		assertEquals(25D, BookSeven.getPrice());
		assertNotNull(BookSeven.getLaunchDate());
        
    }

}
