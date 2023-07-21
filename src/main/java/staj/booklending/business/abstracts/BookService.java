package staj.booklending.business.abstracts;

import staj.booklending.entities.concretes.Book;

public interface BookService {
    public Book getBook(String isbn);
    public Book saveBook(Book book);
    public boolean deleteBook(String isbn);
    public Book updateBook(String isbn, Book book);
}
