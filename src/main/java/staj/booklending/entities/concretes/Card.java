package staj.booklending.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="cards")
@AllArgsConstructor
@NoArgsConstructor
public class Card {

	    @Id
	    @Column(name="card_number")
	    private String cardNumber;
	    
	    @Column(name="balance")
	    private double balance; 
	    
}
