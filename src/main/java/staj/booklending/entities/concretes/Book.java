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
@Table(name="books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {

	@Id
	@Column(name="book_isbn")
	private  String isbn;
	
	@Column(name="title")
	private String title;
	
	@Column(name="author")
	private String author;
	
	@Column(name="daily_Cost")
	private double dailyCost;
}
