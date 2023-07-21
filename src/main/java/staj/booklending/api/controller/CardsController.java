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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import staj.booklending.business.abstracts.CardService;
import staj.booklending.entities.concretes.Card;

@RestController
@RequestMapping("/card")
public class CardsController {

	
	  private final CardService cardService;

	    @Autowired
	    public CardsController(CardService cardService) {
	        this.cardService = cardService;
	    }
	    
	    @GetMapping("/{cardNumber}")
	    public ResponseEntity<Card> getCard(@PathVariable String cardNumber) {
	        Card card = cardService.getCard(cardNumber);
	        if (card != null) {
	            return ResponseEntity.ok(card);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @PostMapping("/add")
	    public ResponseEntity<Card> addCard(@RequestBody Card card) {
	        Card savedCard = cardService.saveCard(card);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
	    }

	    @PutMapping("/update/{cardNumber}")
	    public ResponseEntity<Card> updateCardBalance(@PathVariable String cardNumber, @RequestParam double balance) {
	        Card updatedCard = cardService.updateCard(cardNumber, balance);
	        if(updatedCard == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedCard);
	    }


	    @DeleteMapping("/delete/{cardNumber}")
	    public ResponseEntity<String> deleteCard(@PathVariable String cardNumber) {
	        cardService.deleteCard(cardNumber);
	        return ResponseEntity.ok( cardNumber + " has been deleted.");
	    }
}
