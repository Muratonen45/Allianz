package staj.booklending.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import staj.booklending.business.abstracts.PaymentService;
import staj.booklending.core.exception.ResourceNotFoundException;
import staj.booklending.entities.concretes.Payment;

@ExtendWith(MockitoExtension.class)
public class PaymentsControllerTest {

	
	 @Mock
	    private PaymentService paymentService;

	    @InjectMocks
	    private PaymentsController paymentController;

	    private Payment payment;

	    @BeforeEach
	    public void setUp() {
	    	payment = new Payment();
	        
	    }
	    
	    
	    @Test
	    public void PaymentsController_Get_IdReturnsPayment() {
	    	
	        when(paymentService.getPayment(anyLong())).thenReturn(payment);

	        ResponseEntity<Payment> response = paymentController.getPayment(1L);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(payment, response.getBody());
	    }

	    @Test
	    public void PaymentsController_Get_InvalidIdThrowsException() {
	        // Arrange
	        when(paymentService.getPayment(anyLong())).thenThrow(new ResourceNotFoundException("id", 1L));

	        // Act 
	        ResponseEntity<Payment> responseEntity = paymentController.getPayment(1L);
	        
	     // Assert
	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	    }
	    
	    @Test
	    public void PaymentsController_Add_PaymentAddsPayment() {
	  
	        when(paymentService.savePayment(any(Payment.class))).thenReturn(payment);

	        ResponseEntity<String> response = paymentController.addPayment(payment);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertTrue(response.getBody().contains("is added"));
	    }
	    
	    @Test
	    public void PaymentsController_Update_PaymentAndId_UpdatesPayment() {

	        when(paymentService.updatePayment(anyLong(), any(Payment.class))).thenReturn(payment);

	        ResponseEntity<String> response = paymentController.updatePayment(1L, payment);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertTrue(response.getBody().contains("is updated!"));
	    }
	    
	    @Test
	    public void PaymentsController_Delete_IdDeletesPayment() {

	        when(paymentService.deletePayment(anyLong())).thenReturn(true);

	        ResponseEntity<String> response = paymentController.deletePayment(1L);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertTrue(response.getBody().contains("is deleted!"));
	    }

	    @Test
	    public void PaymentsController_Delete_InvalidIdReturnsNotFound() {

	        when(paymentService.deletePayment(anyLong())).thenReturn(false);

	        ResponseEntity<String> response = paymentController.deletePayment(1L);

	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	    }


	    
	    
	    
	    
}
