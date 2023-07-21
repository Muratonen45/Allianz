package staj.booklending.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import staj.booklending.business.abstracts.UserService;
import staj.booklending.dataAccess.abstracts.UserDao;
import staj.booklending.entities.concretes.User;

@Service
public class UserManager implements UserService{

	private UserDao userDao;
	
	 @Autowired
	    public UserManager(UserDao userDao) {
	        this.userDao = userDao;
	    }
	 
	 @Override
	    public User getUser(String mail) {
	        return (userDao.findById(mail).orElse(null));
	    }

	    @Override
	    public User saveUser(User user) {
	        return userDao.save(user);
	    }

	    @Override
	    public User updateUser(String mail, User user) {
	        User existingUser = userDao.findById(mail).orElse(null);
	        if (existingUser != null) {
	            existingUser.setPassword(user.getPassword());
	            existingUser.setActive(user.isActive());
	            return userDao.save(existingUser);
	        }
	        return null;
	    }

	    @Override
	    public boolean deleteUser(String mail) {
	        User existingUser = userDao.findById(mail).orElse(null);
	        if (existingUser != null) {
	            userDao.delete(existingUser);
	            return true;
	        }
	        return false;
	    }

}
