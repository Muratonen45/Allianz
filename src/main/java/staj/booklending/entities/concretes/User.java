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
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@Column(name="user_mail")
    private String mail;
	
	@Column(name="user_password")
    private String password;
    
	@Column(name="user_active")
    private boolean active;
}
