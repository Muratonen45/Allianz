package staj.booklending.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import staj.booklending.entities.concretes.Payment;

public interface PaymentDao extends JpaRepository<Payment, Long> {
	

    
    @Query("SELECT p.id FROM Payment p WHERE p.book.isbn = :bookIsbn " +
            "AND ((:startDate <= p.startDate AND :endDate >= p.startDate) " +
            "OR (:startDate <= p.endDate AND :endDate >= p.endDate) " +
            "OR (:startDate <= p.startDate AND :endDate >= p.startDate) " +
            "OR (:startDate >= p.startDate AND :endDate <= p.endDate))")
    List<Long> findOverlappingPayments(String bookIsbn, String startDate, String endDate);


	}
