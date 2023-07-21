package staj.booklending.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import staj.booklending.business.abstracts.BookService;
import staj.booklending.entities.concretes.Book;

@RestController
@RequestMapping("/book")
public class BooksController {

	 private BookService bookService;
	 
	 @Autowired
		public BooksController(BookService bookService) {
			super();
			this.bookService = bookService;
		}
	 
	 @GetMapping("/{isbn}")
	    public ResponseEntity<Book> getBook(@PathVariable String isbn) {
	        Book book = bookService.getBook(isbn);
	        if (book != null) {
	            return ResponseEntity.ok(book);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @PostMapping("/add")
	    public ResponseEntity<Book> add(@RequestBody Book book) {
	        Book savedBook = bookService.saveBook(book);
	        if (savedBook != null) {
	            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

	    @DeleteMapping("/delete/{isbn}")
	    public ResponseEntity<String> deleteBook(@PathVariable String isbn) {
	        boolean deleted = bookService.deleteBook(isbn);
	        if (deleted) {
	            return ResponseEntity.ok(isbn + " has been deleted.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @PutMapping("/update/{isbn}")
	    public ResponseEntity<Book> updateBook(@PathVariable String isbn, @RequestBody Book book) {
	        Book updatedBook = bookService.updateBook(isbn, book);
	        if (updatedBook != null) {
	            return ResponseEntity.ok(updatedBook);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}
