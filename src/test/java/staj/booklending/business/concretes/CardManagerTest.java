package staj.booklending.business.concretes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import staj.booklending.dataAccess.abstracts.CardDao;
import staj.booklending.entities.concretes.Card;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CardManagerTest {

	
	 private CardManager cardManager;
	    private CardDao cardDao;
	    private Card card;
	    
	    @BeforeEach
	    public void setup() {
	        cardDao = mock(CardDao.class);
	        cardManager = new CardManager(cardDao);

	        card = new Card();
	        card.setCardNumber("123456");
	        card.setBalance(200.0);
	    }
	    
	    @Test
	    public void CardManager_Get_ReturnsCard() {
	        when(cardDao.findById(anyString())).thenReturn(Optional.of(card));

	        Card result = cardManager.getCard("123456");
	        assertEquals(card, result);
	    }

	    @Test
	    public void CardManager_Get_NonExistingCardReturnsNull() {
	        when(cardDao.findById(anyString())).thenReturn(Optional.empty());

	        Card result = cardManager.getCard("528456");
	        assertNull(result);
	    }
	    
	    @Test
	    public void CardManager_Save_ReturnsSavedCard() {
	        when(cardDao.save(any(Card.class))).thenReturn(card);

	        Card result = cardManager.saveCard(card);
	        assertEquals(card, result);
	    }
	    
	    @Test
	    public void CardManager_Save_NullCardThrowsException() {
	        Card nullCard = null;
	        assertThrows(IllegalArgumentException.class, () -> cardManager.saveCard(nullCard));
	    }
	    
	    
	    
	    @Test
	    public void CardManager_Update_CardUpdatesBalance() {
	       
	        String cardNumber = "1234561234";
	        double originalBalance = 2000.00;
	        double newBalance = 500.00;

	        Card card = new Card();
	        card.setCardNumber(cardNumber);
	        card.setBalance(originalBalance);

	        Card updatedCard = new Card();
	        updatedCard.setCardNumber(cardNumber);
	        updatedCard.setBalance(newBalance);

	        when(cardDao.findById(cardNumber)).thenReturn(Optional.of(card));
	        when(cardDao.save(any(Card.class))).thenReturn(updatedCard);

	       
	        Card result = cardManager.updateCard(cardNumber, newBalance);

	      
	        assertEquals(newBalance, result.getBalance());

	    }

	    @Test
	    public void CardManager_Update_NonExistingCardReturnsNull() {
	        when(cardDao.findById(anyString())).thenReturn(Optional.empty());

	        Card result = cardManager.updateCard("528456", 200.0);
	        assertNull(result);
	    }
	    
	    @Test
	    public void CardManager_Update_NullCardNumberThrowsException() {
	        assertThrows(IllegalArgumentException.class, () -> cardManager.updateCard(null, 200.0));
	    }

	    @Test
	    public void CardManager_Delete_CardDeleteById() {
	        doNothing().when(cardDao).deleteById(anyString());

	        cardManager.deleteCard("123456");
	        verify(cardDao, times(1)).deleteById("123456");
	    }

	    

	    @Test
	    public void CardManager_Delete_NullCardNumberThrowsException() {
	        assertThrows(IllegalArgumentException.class, () -> cardManager.deleteCard(null));
	    }
}
