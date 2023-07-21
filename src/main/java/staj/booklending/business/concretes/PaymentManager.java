package staj.booklending.business.concretes;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import staj.booklending.business.abstracts.PaymentService;
import staj.booklending.core.exception.ResourceNotFoundException;
import staj.booklending.dataAccess.abstracts.BookDao;
import staj.booklending.dataAccess.abstracts.CardDao;
import staj.booklending.dataAccess.abstracts.PaymentDao;
import staj.booklending.dataAccess.abstracts.UserDao;
import staj.booklending.entities.concretes.Book;
import staj.booklending.entities.concretes.Card;
import staj.booklending.entities.concretes.User;
import staj.booklending.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService{

	
	 private final PaymentDao paymentDao;
	    private final UserDao userDao;
	    private final BookDao bookDao;
	    private final CardDao cardDao;

	    @Autowired
	    public PaymentManager(PaymentDao paymentDao, UserDao userDao, BookDao bookDao, CardDao cardDao) {
	        this.paymentDao = paymentDao;
	        this.userDao = userDao;
	        this.bookDao = bookDao;
	        this.cardDao = cardDao;
	    }
	    
	    @Override
	    public Payment getPayment(Long id) {
	        return paymentDao.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException( "id", id));
	    }

	    @Override
	    public Payment savePayment(Payment payment) {
	        User user = userDao.findById(payment.getUser().getMail())
	                .orElseThrow(() -> new ResourceNotFoundException( "mail", payment.getUser().getMail()));

	        Book book = bookDao.findById(payment.getBook().getIsbn())
	                .orElseThrow(() -> new ResourceNotFoundException( "isbn", payment.getBook().getIsbn()));

	        Card card = cardDao.findById(payment.getCard().getCardNumber())
	                .orElseThrow(() -> new ResourceNotFoundException( "cardNumber", payment.getCard().getCardNumber()));

	       
	        if (!isAvailableBetweenDates(book.getIsbn(), payment.getStartDate(), payment.getEndDate())) {
	            throw new IllegalStateException("Book is not available.");
	        }

	        
	        int numberOfDays = calculateNumberOfDays(payment.getStartDate(), payment.getEndDate());
	        double totalCost = calculateTotalCost(book.getDailyCost(), numberOfDays);

	       
	        if (adequateBalance(card.getBalance(), totalCost)) {
	            card.setBalance(card.getBalance() - totalCost);
	            cardDao.save(card);
	            payment.setUser(user);
	            payment.setBook(book);
	            payment.setCard(card);
	            payment.setCost(totalCost);
	            paymentDao.save(payment);
	            return payment;
	        } else {
	            throw new IllegalStateException("Inadequate balance in the credit card.");
	        }
	    }

	    @Override
	    public Payment updatePayment(Long paymentId, Payment payment) {
	        if (payment == null || payment.getUser() == null || payment.getBook() == null || payment.getCard() == null) {
	            throw new IllegalStateException("Payment, User, Book, or Card cannot be null.");
	        }

	        Payment existingPayment = paymentDao.findById(paymentId)
	                .orElseThrow(() -> new ResourceNotFoundException( "id", paymentId));

	        User user = userDao.findById(payment.getUser().getMail())
	                .orElseThrow(() -> new ResourceNotFoundException( "mail", payment.getUser().getMail()));

	        Book book = bookDao.findById(payment.getBook().getIsbn())
	                .orElseThrow(() -> new ResourceNotFoundException( "isbn", payment.getBook().getIsbn()));

	        Card card = cardDao.findById(payment.getCard().getCardNumber())
	                .orElseThrow(() -> new ResourceNotFoundException( "cardNumber", payment.getCard().getCardNumber()));

	        if (!existingPayment.getUser().getMail().equals(payment.getUser().getMail())) {
	            throw new IllegalStateException("User cannot be changed.");
	        }

	        if (!existingPayment.getBook().getIsbn().equals(payment.getBook().getIsbn())) {
	            throw new IllegalStateException("Book cannot be changed.");
	        }

	        if (!existingPayment.getCard().getCardNumber().equals(payment.getCard().getCardNumber())) {
	            throw new IllegalStateException("Card cannot be changed.");
	        }

	        if (!existingPayment.getStartDate().equals(payment.getStartDate()) || !existingPayment.getEndDate().equals(payment.getEndDate())) {
	            int numberOfDays = calculateNumberOfDays(payment.getStartDate(), payment.getEndDate());
	            double totalCost = calculateTotalCost(book.getDailyCost(), numberOfDays);

	            if (!existingPayment.getStartDate().equals(payment.getStartDate()) || !existingPayment.getEndDate().equals(payment.getEndDate())) {
	                
	                existingPayment.getCard().setBalance(existingPayment.getCard().getBalance() + existingPayment.getCost());
	                
	                if (!adequateBalance(card.getBalance(), totalCost)) {
	                    throw new IllegalStateException("Inadequate balance in the credit card.");
	                }
	                card.setBalance(card.getBalance() - totalCost);
	                cardDao.save(card);
	            }

	            existingPayment.setStartDate(payment.getStartDate());
	            existingPayment.setEndDate(payment.getEndDate());
	            existingPayment.setCost(totalCost);
	        }
	        paymentDao.save(existingPayment);
	        return existingPayment;
	    }

	    @Override
	    public boolean deletePayment(Long paymentId) {
	        Payment payment = paymentDao.findById(paymentId)
	                .orElseThrow(() -> new ResourceNotFoundException( "id", paymentId));

	        paymentDao.delete(payment);
	        return true;
	    }

	    private int calculateNumberOfDays(String startDate, String endDate) {
	        LocalDate start = LocalDate.parse(startDate);
	        LocalDate end = LocalDate.parse(endDate);
	        return (int) start.until(end).getDays();
	    }

	   
	    private double calculateTotalCost(double dailyCost, int numberOfDays) {
	        return dailyCost * numberOfDays;
	    }

	  
	    
	    private boolean isAvailableBetweenDates(String bookIsbn, String startDate, String endDate) {
	        List<Long> overlappingPayments = paymentDao.findOverlappingPayments(bookIsbn, startDate, endDate);
	        return overlappingPayments.isEmpty();
	    }

	   
	    private boolean adequateBalance(double balance, double cost) {
	        return (balance >= cost);
	    }
}
