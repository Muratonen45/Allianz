package staj.booklending.business.concretes;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import staj.booklending.dataAccess.abstracts.UserDao;
import staj.booklending.entities.concretes.User;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {
	
	 @InjectMocks
	    UserManager userManager;

	    @Mock
	    UserDao userDao;

	    @BeforeEach
	   public void setUp() {
	        MockitoAnnotations.initMocks(this);
	    }
	    
	    
	    @Test
	    public void UserManager_Get_MailReturnsUser() {
	        User user = new User();
	        user.setMail("mai@mail.com");

	        when(userDao.findById(anyString())).thenReturn(Optional.of(user));

	        User result = userManager.getUser("mail@mail.com");

	        assertNotNull(result);
	        assertEquals("mail@mail.com", result.getMail());
	    }

	    @Test
	    public void UserManager_Get_InvalidMailReturnsNull() {
	        when(userDao.findById(anyString())).thenReturn(Optional.empty());

	        User result = userManager.getUser("mail@mail.com");

	        assertNull(result);
	    }
	    
	    @Test
	    public void UserManager_Save_UserReturnsUser() {
	        User user = new User();
	        user.setMail("test@mail.com");

	        when(userDao.save(any(User.class))).thenReturn(user);

	        User result = userManager.saveUser(user);

	        assertNotNull(result);
	        assertEquals("mail@mail.com", result.getMail());
	    }
	    
	    @Test
	    public void UserManager_Update_MailUpdatesUser() {
	        User user = new User();
	        user.setMail("mail@mail.com");
	        user.setPassword("password");
	        user.setActive(true);

	        User updatedUser = new User();
	        updatedUser.setMail("mail@mail.com");
	        updatedUser.setPassword("new_password");
	        updatedUser.setActive(false);

	        when(userDao.findById(anyString())).thenReturn(Optional.of(user));
	        when(userDao.save(any(User.class))).thenReturn(updatedUser);

	        User result = userManager.updateUser("mail@mail.com", updatedUser);

	        assertNotNull(result);
	        assertEquals("new_password", result.getPassword());
	        assertFalse(result.isActive());
	    }
	    
	    @Test
	    public void UserManager_Delete_MailReturnsTrue() {
	        User user = new User();
	        user.setMail("mail@mail.com");

	        when(userDao.findById(anyString())).thenReturn(Optional.of(user));

	        boolean result = userManager.deleteUser("mail@mail.com");

	        assertTrue(result);
	        verify(userDao, times(1)).delete(user);
	    }

	    @Test
	    public void UserManager_Delete_InvalidMailReturnsFalse() {
	        when(userDao.findById(anyString())).thenReturn(Optional.empty());

	        boolean result = userManager.deleteUser("mail@mail.com");

	        assertFalse(result);
	        verify(userDao, never()).delete(any(User.class));
	    }

	    
	    
	
}