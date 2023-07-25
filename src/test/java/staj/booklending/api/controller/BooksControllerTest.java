package staj.booklending.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import staj.booklending.business.abstracts.BookService;
import staj.booklending.entities.concretes.Book;

public class BooksControllerTest {

	
	 @InjectMocks
	    private BooksController booksController;

	    @Mock
	    private BookService bookService;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }
	    
	    
	    @Test
	    public void BooksController_Get_ReturnsBook() {
	        Book book = new Book();
	        book.setIsbn("123456");
	        when(bookService.getBook(anyString())).thenReturn(book);
	        ResponseEntity<Book> responseEntity = booksController.getBook("123456");
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(book, responseEntity.getBody());
	    }

	    @Test
	    public void BooksController_Get_NonExistentIsbn_ReturnsNotFound() {
	        when(bookService.getBook(anyString())).thenReturn(null);
	        ResponseEntity<Book> responseEntity = booksController.getBook("123456");
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	    }

	    @Test
	    public void BooksController_Add_Book_ReturnsCreated() {
	        Book book = new Book();
	        book.setIsbn("123456");
	        when(bookService.saveBook(any(Book.class))).thenReturn(book);
	        ResponseEntity<Book> responseEntity = booksController.add(book);
	        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	        assertEquals(book, responseEntity.getBody());
	    }

	    @Test
	    public void BooksController_Delete_Isbn_ReturnsOK() {
	        when(bookService.deleteBook(anyString())).thenReturn(true);
	        ResponseEntity<String> responseEntity = booksController.deleteBook("123456");
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    }

	    @Test
	    public void BooksController_Delete_NonExistentIsbn_ReturnsNotFound() {
	        when(bookService.deleteBook(anyString())).thenReturn(false);
	        ResponseEntity<String> responseEntity = booksController.deleteBook("123456");
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	    }

	    @Test
	    public void BooksController_Update_Isbn_ReturnsUpdatedBook() {
	        Book book = new Book();
	        book.setIsbn("123456");
	        when(bookService.updateBook(anyString(), any(Book.class))).thenReturn(book);
	        ResponseEntity<Book> responseEntity = booksController.updateBook("123456", book);
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(book, responseEntity.getBody());
	    }

	    @Test
	    public void BooksController_Update_NonExistentIsbn_ReturnsNotFound() {
	        when(bookService.updateBook(anyString(), any(Book.class))).thenReturn(null);
	        ResponseEntity<Book> responseEntity = booksController.updateBook("123456", new Book());
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	    }

	    
	    
	    
	    
}
