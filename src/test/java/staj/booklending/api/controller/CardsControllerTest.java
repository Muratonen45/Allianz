package staj.booklending.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import staj.booklending.business.abstracts.CardService;
import staj.booklending.entities.concretes.Card;

public class CardsControllerTest {

	
	 @InjectMocks
	    private CardsController cardController;

	    @Mock
	    private CardService cardService;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }
	    
	    
	    @Test
	    public void CardsController_Get_ReturnsCard() {
	        Card card = new Card();
	        card.setCardNumber("123456");
	        when(cardService.getCard(anyString())).thenReturn(card);
	        ResponseEntity<Card> responseEntity = cardController.getCard("1234");
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(card, responseEntity.getBody());
	    }

	    @Test
	    public void CardsController_Get_NonExistentCardNumber_ReturnsNotFound() {
	        when(cardService.getCard(anyString())).thenReturn(null);
	        ResponseEntity<Card> responseEntity = cardController.getCard("123456");
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	    }
	    
	    @Test
	    public void CardsController_Add_Card_ReturnsCreated() {
	        Card card = new Card();
	        card.setCardNumber("123456");
	        when(cardService.saveCard(any(Card.class))).thenReturn(card);
	        ResponseEntity<Card> responseEntity = cardController.addCard(card);
	        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	        assertEquals(card, responseEntity.getBody());
	    }
	     

	    @Test
	    public void CardsController_Add_NullCardNumber_ReturnsBadRequest() {
	        Card card = new Card();
	        card.setCardNumber(null);
	        when(cardService.saveCard(any(Card.class))).thenThrow(NullPointerException.class);
	        assertThrows(NullPointerException.class, () -> cardController.addCard(card));
	    }
	    
	    
	    @Test
	    public void CardsController_Update_CardNumber_ReturnsUpdatedCard() {
	        Card card = new Card();
	        card.setCardNumber("123456");
	        card.setBalance(200.00);
	        when(cardService.updateCard(anyString(), anyDouble())).thenReturn(card);
	        ResponseEntity<Card> responseEntity = cardController.updateCardBalance("123456", 200.00);
	        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	        assertEquals(card, responseEntity.getBody());
	    }

	    @Test
	    public void CardsController_Update_NonExistentCardNumber_ReturnsNotFound() {
	        when(cardService.updateCard(anyString(), anyDouble())).thenReturn(null);
	        ResponseEntity<Card> responseEntity = cardController.updateCardBalance("123456", 200.00);
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	    }
	    
	    @Test
	    public void CardsController_Update_NegativeBalance_ReturnsBadRequest() {
	        Card card = new Card();
	        card.setCardNumber("123456");
	        card.setBalance(-1.0);
	        when(cardService.updateCard(anyString(), anyDouble())).thenThrow(IllegalArgumentException.class);
	        assertThrows(IllegalArgumentException.class, () -> cardController.updateCardBalance("123456", -1.0));
	    }

	    @Test
	    public void CardsController_Update_ExceedingBalanceLimit_ReturnsBadRequest() {
	        Card card = new Card();
	        card.setCardNumber("1234");
	        card.setBalance(1_000.0); 
	        when(cardService.updateCard(anyString(), anyDouble())).thenThrow(IllegalArgumentException.class);
	        assertThrows(IllegalArgumentException.class, () -> cardController.updateCardBalance("123456", 1_000.0));
	    }
	    
	    @Test
	    public void CardsController_Delete_CardNumber_ReturnsOk() {
	        doNothing().when(cardService).deleteCard(anyString());
	        ResponseEntity<String> responseEntity = cardController.deleteCard("123456");
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    }


	    
	    
	    
	    

}
