package staj.booklending.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import staj.booklending.entities.concretes.User;

public interface UserDao extends JpaRepository<User, String> {


}
