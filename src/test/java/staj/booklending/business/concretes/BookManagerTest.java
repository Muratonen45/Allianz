package staj.booklending.business.concretes;


import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import staj.booklending.dataAccess.abstracts.BookDao;
import staj.booklending.entities.concretes.Book;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookManagerTest {

    @Mock
    private BookDao bookDao;
    @InjectMocks
    private BookManager bookManager;

    @Test
    public void BookManager_Get_ReturnsBook() {
        String isbn = "777-5-15-157440-0";
        Book book = new Book();
        book.setIsbn(isbn);
        when(bookDao.findById(isbn)).thenReturn(Optional.of(book));
        Book fetchedBook = bookManager.getBook(isbn);
        assertEquals(isbn, fetchedBook.getIsbn());
    }
    
    public void BookManager_Get_NonExistingBookReturnsNull() {
        String isbn = "777-5-15-157440-0";
        when(bookDao.findById(isbn)).thenReturn(Optional.empty());
        Book fetchedBook = bookManager.getBook(isbn);
        assertNull(fetchedBook);
    }


    @Test
    public void BookManager_Save_ReturnsSavedBook() {
        String isbn = "777-5-15-157440-0";
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        when(bookDao.save(any(Book.class))).thenReturn(book);
        Book savedBook = bookManager.saveBook(book);
        assertNotNull(savedBook);
    }
    

    @Test
    public void BookMnager_Save_NullBookThrowsException() {
        Book book = null;
        assertThrows(IllegalArgumentException.class, () -> bookManager.saveBook(book));
    }

    @Test
    public void BookManager_Delete_DeletesBook() {
        String isbn = "777-5-15-157440-0";
        Book book = new Book();
        book.setIsbn(isbn);
        when(bookDao.findById(isbn)).thenReturn(Optional.of(book));
        boolean isDeleted = bookManager.deleteBook(isbn);
        assertTrue(isDeleted);
        verify(bookDao, times(1)).delete(book);
    }
    
    @Test
    public void BookManager_Delete_NonExistingBookReturnsFalse() {
        String isbn = "777-5-15-157440-0";
        when(bookDao.findById(isbn)).thenReturn(Optional.empty());
        boolean isDeleted = bookManager.deleteBook(isbn);
        assertFalse(isDeleted);
    }

    @Test
    public void BookManager_Update_ReturnsUpdatedBook() {
        String isbn = "777-5-15-157440-0";
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        when(bookDao.findById(isbn)).thenReturn(Optional.of(book));
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Test Book");
        updatedBook.setAuthor("Updated Test Author");
        when(bookDao.save(any(Book.class))).thenReturn(updatedBook);
        Book newBook = bookManager.updateBook(isbn, updatedBook);
        assertEquals("Updated Test Book", newBook.getTitle());
        assertEquals("Updated Test Author", newBook.getAuthor());
    }
    
    @Test
    public void BookManager_Update_NonExistingBookReturnsNull() {
        String isbn = "777-5-15-157440-0";
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Test Book");
        updatedBook.setAuthor("Updated Test Author");
        when(bookDao.findById(isbn)).thenReturn(Optional.empty());
        Book newBook = bookManager.updateBook(isbn, updatedBook);
        assertNull(newBook);
    }

   
    @Test
    public void BookManager_SaveAndVerify_VerifiesSavedBook() {
        String isbn = "777-5-15-157440-0";
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        when(bookDao.save(any(Book.class))).thenAnswer(i -> i.getArguments()[0]);
        Book savedBook = bookManager.saveBook(book);
        verify(bookDao, times(1)).save(book);
        assertEquals(book, savedBook);
    }

   
    
   


}