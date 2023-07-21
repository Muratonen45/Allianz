package staj.booklending.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import staj.booklending.business.abstracts.BookService;
import staj.booklending.dataAccess.abstracts.BookDao;
import staj.booklending.entities.concretes.Book;

@Service
public class BookManager implements BookService {

	
	    private BookDao bookDao;
	 
	 @Autowired
	    public BookManager(BookDao bookDao) {
	        this.bookDao = bookDao;
	    }

	    @Override
	    public Book getBook(String isbn) {
	        return (bookDao.findById(isbn).orElse(null));
	    }

	    @Override
	    public Book saveBook(Book book) {
	        if (book == null || book.getTitle() == null || book.getAuthor() == null) {
	            throw new IllegalStateException("Invalid book");
	        }
	        return bookDao.save(book);
	    }

	    @Override
	    public boolean deleteBook(String isbn) {
	        Book existingBook = bookDao.findById(isbn).orElse(null);
	        if (existingBook != null) {
	            bookDao.delete(existingBook);
	            return true;
	        }
	        return false;
	    }

	    @Override
	    public Book updateBook(String isbn, Book book) {
	        Book existingBook = bookDao.findById(isbn).orElse(null);
	        if (existingBook != null) {
	            existingBook.setAuthor(book.getAuthor());
	            existingBook.setTitle(book.getTitle());
	            existingBook.setDailyCost(book.getDailyCost());
	            return bookDao.save(existingBook);
	        }
	        return null;
	    }
}
