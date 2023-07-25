package staj.booklending.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import staj.booklending.business.abstracts.UserService;
import staj.booklending.entities.concretes.User;

@ExtendWith(MockitoExtension.class)
public class UsersControllerTest {

	
	 @Mock
	    private UserService userService;

	    @InjectMocks
	    private UsersController userController;
	    
	    
	    @Test
	    public void UsersController_Get_ReturnsUser() {
	        User user = new User();
	        user.setMail("mail@mail.com");
	        user.setPassword("password");
	        user.setActive(true);
	        when(userService.getUser(anyString())).thenReturn(user);
	        ResponseEntity<User> response = userController.getUser("mail@mail.com");
	        assertEquals(200, response.getStatusCode());
	    }
	    
	    @Test
	    public void UsersController_Get_UserNotExist_ThrowsException() {
	        when(userService.getUser(anyString())).thenReturn(null);
	        ResponseEntity<User> response = userController.getUser("mail@mail.com");
	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	    }
	    
	    @Test
	    public void UsersController_Add_ReturnsUser() {
	        User user = new User();
	        user.setMail("mail@mail.com");
	        user.setPassword("password");
	        user.setActive(true);
	        when(userService.saveUser(any(User.class))).thenReturn(user);
	        ResponseEntity<String> response = userController.addUser(user);
	        assertEquals(200, response.getStatusCode());
	    }
	    
	    @Test
	    public void UsersController_Add_NullUser_ThrowsException() {
	        when(userService.saveUser(null)).thenThrow(new IllegalArgumentException("User cannot be null"));
	        ResponseEntity<String> response = userController.addUser(null);
	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	    }
	    
	    @Test
	    public void UsersController_Update_ReturnsUser() {
	        User user = new User();
	        user.setMail("mail@mail.com");
	        user.setPassword("password");
	        user.setActive(true);
	        when(userService.updateUser(anyString(), any(User.class))).thenReturn(user);
	        ResponseEntity<String> response = userController.updateUser("mail@mail.com", user);
	        assertEquals(200, response.getStatusCode());
	    }
	    
	    @Test
	    public void UsersController_Update_UserNotExist_ThrowsException() {
	        User user = new User();
	        user.setMail("mail@mail.com");
	        user.setPassword("password");
	        user.setActive(true);
	        when(userService.updateUser(anyString(), any(User.class))).thenReturn(null);
	        ResponseEntity<String> response = userController.updateUser("mail@mail.com", user);
	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	    }
	    
	    @Test
	    public void UsersController_Delete_ReturnsUser() {
	        when(userService.deleteUser(anyString())).thenReturn(true);
	        ResponseEntity<String> response = userController.deleteUser("mail@mail.com");
	        assertEquals(200, response.getStatusCode());
	    }
	    
	    @Test
	    public void UsersController_Delete_UserNotExist_ThrowsException() {
	            when(userService.deleteUser(anyString())).thenReturn(false);
	        ResponseEntity<String> response = userController.deleteUser("mail@mail.com");
	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	    }


}
