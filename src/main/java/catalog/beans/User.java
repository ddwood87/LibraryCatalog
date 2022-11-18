package catalog.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
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
public class User {
	@Id
	@GeneratedValue
	int id;
	String firstName;
	String lastName;
	String phone;
	
	public User(String firstName, String lastName, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}
	public User(String json) {
		JsonToUserConverter converter = new JsonToUserConverter();
		User u = converter.convert(json);
		this.id = u.getId();
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
}
