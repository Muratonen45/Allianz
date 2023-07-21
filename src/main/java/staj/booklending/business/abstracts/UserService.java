package staj.booklending.business.abstracts;

import staj.booklending.entities.concretes.User;

public interface UserService {
    User getUser(String mail);
    User saveUser(User user);
    User updateUser(String mail, User user);
    boolean deleteUser(String mail);
}