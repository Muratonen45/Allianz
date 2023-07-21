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
import org.springframework.web.bind.annotation.RestController;
import staj.booklending.business.abstracts.UserService;
import staj.booklending.entities.concretes.User;

@RestController
@RequestMapping("/user")
public class UsersController {

	
    private UserService userService;
    
    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/{mail}")
    public ResponseEntity<User> getUser(@PathVariable String mail) {
        try {
            User user = userService.getUser(mail);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            if (savedUser != null) {
                return ResponseEntity.ok(savedUser.getMail() + " is added " +
                        "Password: " + savedUser.getPassword() + "\n" +
                        "Active status: " + savedUser.isActive());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add the user.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/update/{mail}")
    public ResponseEntity<String> updateUser(@PathVariable String mail, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(mail, user);
            if (updatedUser != null) {
                return ResponseEntity.ok( mail + " is updated " +
                        "New password: " + updatedUser.getPassword() + "\n" +
                        "Active status: " + updatedUser.isActive());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{mail}")
    public ResponseEntity<String> deleteUser(@PathVariable String mail) {
        try {
            boolean result = userService.deleteUser(mail);
            if (result) {
                return ResponseEntity.ok(mail + " is deleted ");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
