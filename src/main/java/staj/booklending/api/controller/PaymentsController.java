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

import staj.booklending.business.abstracts.PaymentService;
import staj.booklending.entities.concretes.Payment;

@RestController
@RequestMapping("/payment")
public class PaymentsController {

	private final PaymentService paymentService;

    @Autowired
    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
            Payment payment = paymentService.getPayment(id);
            return ResponseEntity.ok(payment);
        
           
        
    }
    @PostMapping("/add")
    public ResponseEntity<String> addPayment(@RequestBody Payment payment) {
            Payment savedPayment = paymentService.savePayment(payment);
            return ResponseEntity.ok(savedPayment.getId() + " is added!");
       
        
    }

    @PutMapping("/update/{paymentId}")
    public ResponseEntity<String> updatePayment(@PathVariable Long paymentId, @RequestBody Payment payment) {
        
            Payment updatedPayment = paymentService.updatePayment(paymentId, payment);
            return ResponseEntity.ok(updatedPayment.getId() + " is updated!");
      
    }

    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable Long paymentId) {
        
            boolean result = paymentService.deletePayment(paymentId);
            if (result) {
                return ResponseEntity.ok( paymentId + " is deleted!");
            } else {
                return ResponseEntity.notFound().build();
            }
        
    }
}
