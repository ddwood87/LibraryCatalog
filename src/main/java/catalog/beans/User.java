package catalog.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Entity
public class User {
	@Id
	@GeneratedValue
	int id;
	String lastName;
	String firstName;
	int phone;
}
