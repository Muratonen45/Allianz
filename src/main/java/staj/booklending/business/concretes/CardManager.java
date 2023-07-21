package staj.booklending.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import staj.booklending.business.abstracts.CardService;
import staj.booklending.dataAccess.abstracts.CardDao;
import staj.booklending.entities.concretes.Card;

@Service
public class CardManager implements CardService{

	private final CardDao cardDao;

    @Autowired
    public CardManager(CardDao cardDao) {
        this.cardDao = cardDao;
    }
    
    @Override
    public Card getCard(String cardNumber) {
        return cardDao.findById(cardNumber).orElse(null);
    }

    @Override
    public Card saveCard(Card card) {
        if (card == null) {
            throw new IllegalStateException("Card cannot be null");
        }
        return cardDao.save(card);
    }

    @Override
    public Card updateCard(String cardNumber, Double balance) {
        if (cardNumber == null) {
            throw new IllegalStateException("Card number cannot be null");
        }

        Card existingCard = cardDao.findById(cardNumber).orElse(null);
        if (existingCard != null) {
            existingCard.setBalance(balance);
            return cardDao.save(existingCard);
        }
        return null;
    }


    @Override
    public void deleteCard(String cardNumber) {
        if (cardNumber == null) {
            throw new IllegalStateException("Card number cannot be null");
        }
        cardDao.deleteById(cardNumber);
    }
}
