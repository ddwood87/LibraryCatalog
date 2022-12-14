package catalog.beans;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_class", 
					discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("0")
public class User {
	@Id
	@GeneratedValue
	int id;
	@Column(unique=true)
	String userName;
	String firstName;
	String lastName;
	String phone;
	@Getter(AccessLevel.NONE)
	String password;
	
	public User(String userName, String firstName, String lastName, String phone) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}
	public User(String json) {
		JsonToUserConverter converter = new JsonToUserConverter();
		User u = converter.convert(json);
		this.userName = u.getUserName();
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
		this.phone = u.getPhone();
	}
	public boolean equals(User anotherUser) {
		if(anotherUser != null) {
			
			if(this.lastName.equals(anotherUser.lastName) 
					&& this.firstName.equals(anotherUser.firstName) 
					&& this.phone.equals(anotherUser.phone)) {
				return true;		
			}
		}
		return false;
	}
	
	public boolean checkPassword(String password) {
		if(this.password.equals(password)) {
			return true;
		}else { return false; }
	}
}
