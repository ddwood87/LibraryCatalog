package catalog.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue
	int id;
	String lastName;
	String firstName;
	String phone;
	
	
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
