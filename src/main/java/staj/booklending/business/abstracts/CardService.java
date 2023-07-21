package staj.booklending.business.abstracts;

import staj.booklending.entities.concretes.Card;

public interface CardService {
    Card getCard(String cardNumber);
    Card saveCard(Card card);
    Card updateCard(String cardNumber, Double balance);
    void deleteCard(String cardNumber);
}
