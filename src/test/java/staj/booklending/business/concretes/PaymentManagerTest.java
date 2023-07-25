package staj.booklending.business.concretes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import staj.booklending.core.exception.ResourceNotFoundException;
import staj.booklending.dataAccess.abstracts.BookDao;
import staj.booklending.dataAccess.abstracts.CardDao;
import staj.booklending.dataAccess.abstracts.PaymentDao;
import staj.booklending.dataAccess.abstracts.UserDao;
import staj.booklending.entities.concretes.Book;
import staj.booklending.entities.concretes.Card;
import staj.booklending.entities.concretes.Payment;
import staj.booklending.entities.concretes.User;

public class PaymentManagerTest {

	
	 @InjectMocks
	    private PaymentManager paymentManager;

	    @Mock
	    private PaymentDao paymentDao;

	    @Mock
	    private UserDao userDao;

	    @Mock
	    private BookDao bookDao;

	    @Mock
	    private CardDao cardDao;
	    
	    @BeforeEach
	   public void setUp() {
	        MockitoAnnotations.initMocks(this);;
	    }
	    
	    
	    @Test
	   public void PaymentManager_Get_PaymentReturnsPayment() {
	        Payment payment = new Payment();
	        payment.setId(1L);

	        when(paymentDao.findById(anyLong())).thenReturn(Optional.of(payment));

	        Payment result = paymentManager.getPayment(1L);

	        assertNotNull(result);
	        assertEquals(2L, result.getId());
	    }
	    
	    @Test
	   public void PaymentManager_Get_InvalidIdThrowsException() {
	        when(paymentDao.findById(anyLong())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> paymentManager.getPayment(1L));
	    }
	    
	    @Test
	   public void PaymentManager_Save_ValidPaymentReturnsPayment() {
	        Payment payment = new Payment();
	        User user = new User();
	        Book book = new Book();
	        Card card = new Card();
	        user.setMail("murat@mail.com");
	        book.setIsbn("1234");
	        card.setCardNumber("1212");
	        card.setBalance(500);
	        book.setDailyCost(20);
	        payment.setUser(user);
	        payment.setBook(book);
	        payment.setCard(card);
	        payment.setStartDate("2023-07-15");
	        payment.setEndDate("2023-07-18");

	        when(userDao.findById(anyString())).thenReturn(Optional.of(user));
	        when(bookDao.findById(anyString())).thenReturn(Optional.of(book));
	        when(cardDao.findById(anyString())).thenReturn(Optional.of(card));
	        when(paymentDao.findOverlappingPayments(anyString(), anyString(), anyString())).thenReturn(Collections.emptyList());
	        when(paymentDao.save(any(Payment.class))).thenReturn(payment);

	        Payment result = paymentManager.savePayment(payment);

	        assertNotNull(result);
	        assertEquals(30, result.getCost());
	    }
	    
	    @Test
	   public void PaymentManager_Save_NonExistentUserThrowsException() {
	        Payment payment = new Payment();
	        User user = new User();
	        user.setMail("murat@mail.com");
	        payment.setUser(user);

	        when(userDao.findById(anyString())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> paymentManager.savePayment(payment));
	    }
	    
	    @Test
	   public void PaymentManager_Save_NonExistentBookThrowsException() {
	        
	        User user = new User();
	        user.setMail("murat@mail.com");

	        Card card = new Card();
	        card.setCardNumber("1234567891");

	        Book book = new Book();
	        book.setIsbn("123456");

	        Payment payment = new Payment();
	        payment.setUser(user);
	        payment.setBook(book);
	        payment.setCard(card);
	        payment.setStartDate("2023-07-14");
	        payment.setEndDate("2023-07-18");

	        when(bookDao.findById(anyString())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> paymentManager.savePayment(payment));
	    }
	    
	    @Test
	   public void PaymentManager_Save_NonExistentCardThrowsException() {
	        Payment payment = new Payment();
	        User user = new User();
	        Book book = new Book();
	        Card card = new Card();
	        user.setMail("murat@mail.com");
	        book.setIsbn("123456");
	        card.setCardNumber("non-existent-card-number");
	        card.setBalance(500);
	        book.setDailyCost(20);
	        payment.setUser(user);
	        payment.setBook(book);
	        payment.setCard(card);
	        payment.setStartDate("2023-07-14");
	        payment.setEndDate("2023-07-18");

	        when(userDao.findById(anyString())).thenReturn(Optional.of(user));
	        when(bookDao.findById(anyString())).thenReturn(Optional.of(book));
	        when(cardDao.findById(anyString())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> paymentManager.savePayment(payment));
	    }
	    
	    @Test
	   public void PaymentManager_Save_BookUnavailableThrowsException() {
	        Payment payment = new Payment();
	        User user = new User();
	        Book book = new Book();
	        Card card = new Card();
	        user.setMail("murat@mail.com");
	        book.setIsbn("123456");
	        card.setCardNumber("1111");
	        card.setBalance(500);
	        book.setDailyCost(20);
	        payment.setUser(user);
	        payment.setBook(book);
	        payment.setCard(card);
	        payment.setStartDate("2023-07-14");
	        payment.setEndDate("2023-07-18");

	        when(userDao.findById(anyString())).thenReturn(Optional.of(user));
	        when(bookDao.findById(anyString())).thenReturn(Optional.of(book));
	        when(cardDao.findById(anyString())).thenReturn(Optional.of(card));
	        when(paymentDao.findOverlappingPayments(anyString(), anyString(), anyString())).thenReturn(Arrays.asList(1L, 2L, 3L));

	        assertThrows(IllegalArgumentException.class, () -> paymentManager.savePayment(payment));
	    }
	    
	    @Test
	   public void PaymentManager_Save_AdequateBalanceThrowsException() {
	        Payment payment = new Payment();
	        User user = new User();
	        Book book = new Book();
	        Card card = new Card();
	        user.setMail("murat@mail.com");
	        book.setIsbn("123456");
	        card.setCardNumber("1111");
	        card.setBalance(10); 
	        book.setDailyCost(40); 
	        payment.setUser(user);
	        payment.setBook(book);
	        payment.setCard(card);
	        payment.setStartDate("2023-07-14");
	        payment.setEndDate("2023-07-18");

	        when(userDao.findById(anyString())).thenReturn(Optional.of(user));
	        when(bookDao.findById(anyString())).thenReturn(Optional.of(book));
	        when(cardDao.findById(anyString())).thenReturn(Optional.of(card));
	        when(paymentDao.findOverlappingPayments(anyString(), anyString(), anyString())).thenReturn(Collections.emptyList());

	        assertThrows(IllegalArgumentException.class, () -> paymentManager.savePayment(payment));
	    }
	    
	    @Test
	   public void PaymentManager_Update_PaymentReturnsPayment() {
	        Payment existingPayment = new Payment();
	        Payment updatedPayment = new Payment();
	        User user = new User();
	        Book book = new Book();
	        Card card = new Card();

	        user.setMail("murat@mail.com");
	        book.setIsbn("123456");
	        card.setCardNumber("1111");
	        card.setBalance(500);
	        book.setDailyCost(20);

	        existingPayment.setUser(user);
	        existingPayment.setBook(book);
	        existingPayment.setCard(card);
	        existingPayment.setStartDate("2023-07-14");
	        existingPayment.setEndDate("2023-07-18");

	        updatedPayment.setUser(user);
	        updatedPayment.setBook(book);
	        updatedPayment.setCard(card);
	        updatedPayment.setStartDate("2023-07-14");
	        updatedPayment.setEndDate("2023-07-19");

	        when(paymentDao.findById(anyLong())).thenReturn(Optional.of(existingPayment));
	        when(userDao.findById(anyString())).thenReturn(Optional.of(user));
	        when(bookDao.findById(anyString())).thenReturn(Optional.of(book));
	        when(cardDao.findById(anyString())).thenReturn(Optional.of(card));

	        Payment result = paymentManager.updatePayment(2L, updatedPayment);

	        assertEquals("2023-07-14", result.getStartDate());
	        assertEquals("2023-07-19", result.getEndDate());
	        assertEquals(50.0, result.getCost());
	    }
	    
	    @Test
	   public void PaymentManager_Update_NonExistentPaymentThrowsException() {
	        Payment payment = new Payment();
	        payment.setId(1L);

	        when(paymentDao.findById(anyLong())).thenReturn(Optional.empty());

	        assertThrows(IllegalArgumentException.class, () -> paymentManager.updatePayment(1L, payment));
	    }
	    
	    @Test
	   public void PaymentManager_Update_UserThrowsException() {
	        Payment existingPayment = new Payment();
	        User existingUser = new User();
	        User user = new User();
	        existingUser.setMail("existing@mail.com");
	        user.setMail("user@mail.com");
	        existingPayment.setUser(existingUser);

	        Payment updatedPayment = new Payment();
	        updatedPayment.setUser(user);
	        updatedPayment.setBook(new Book());  
	        updatedPayment.setCard(new Card());  

	        when(paymentDao.findById(anyLong())).thenReturn(Optional.of(existingPayment));

	        assertThrows(ResourceNotFoundException.class, () -> paymentManager.updatePayment(1L, updatedPayment));
	    }

	    @Test
	   public void PaymentManager_Update_BookThrowsException() {
	        Payment existingPayment = new Payment();
	        Book existingBook = new Book();
	        Book newBook = new Book();
	        existingBook.setIsbn("1111");
	        newBook.setIsbn("1212");
	        existingPayment.setBook(existingBook);

	        Payment updatedPayment = new Payment();
	        updatedPayment.setBook(newBook);
	        updatedPayment.setUser(new User());  
	        updatedPayment.setCard(new Card());  

	        when(paymentDao.findById(anyLong())).thenReturn(Optional.of(existingPayment));

	        assertThrows(ResourceNotFoundException.class, () -> paymentManager.updatePayment(1L, updatedPayment));
	    }

	    @Test
	   public void PaymentManager_Update_CardThrowsException() {
	        Payment existingPayment = new Payment();
	        Card existingCard = new Card();
	        Card newCard = new Card();
	        existingCard.setCardNumber("1111");
	        newCard.setCardNumber("1212");
	        existingPayment.setCard(existingCard);

	        Payment updatedPayment = new Payment();
	        updatedPayment.setCard(newCard);
	        updatedPayment.setUser(new User());  
	        updatedPayment.setBook(new Book()); 

	        when(paymentDao.findById(anyLong())).thenReturn(Optional.of(existingPayment));

	        assertThrows(ResourceNotFoundException.class, () -> paymentManager.updatePayment(1L, updatedPayment));
	    }

	    
	    
	    @Test
	   public void PaymentManager_Delete_PaymentReturnsTrue() {
	        Payment payment = new Payment();
	        payment.setId(1L);

	        when(paymentDao.findById(anyLong())).thenReturn(Optional.of(payment));

	        assertTrue(paymentManager.deletePayment(2L));
	    }

	    @Test
	   public void PaymentManager_Delete_NonExistentPaymentThrowsException() {
	        when(paymentDao.findById(anyLong())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> paymentManager.deletePayment(1L));
	    }


	    
	    
	    
	    
	    
	    
	    

}
