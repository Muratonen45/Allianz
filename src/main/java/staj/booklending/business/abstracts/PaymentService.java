package staj.booklending.business.abstracts;


import staj.booklending.entities.concretes.Payment;

public interface PaymentService {
    Payment getPayment(Long id);
    Payment savePayment(Payment payment);
    Payment updatePayment(Long id, Payment payment);
    boolean deletePayment(Long id);
}