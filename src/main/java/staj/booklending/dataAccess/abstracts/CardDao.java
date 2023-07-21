package staj.booklending.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import staj.booklending.entities.concretes.Card;

public interface CardDao extends JpaRepository<Card, String>{


}
