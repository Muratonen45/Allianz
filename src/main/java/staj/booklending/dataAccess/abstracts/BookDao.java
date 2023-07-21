package staj.booklending.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import staj.booklending.entities.concretes.Book;

public interface BookDao  extends JpaRepository<Book, String>{

}
