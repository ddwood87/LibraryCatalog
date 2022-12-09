package catalog.beans;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@DiscriminatorValue("2")
public class Librarian extends Borrower {
	double salary;
	double hours;
	boolean admin;
	
	public Librarian(User user) {
		this.id = user.id;
		this.userName = user.userName;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.phone = user.phone;
		this.password = user.password;
	}
	
	public Librarian(Borrower borrower) {
		this.id = borrower.id;
		this.userName = borrower.userName;
		this.firstName = borrower.firstName;
		this.lastName = borrower.lastName;
		this.phone = borrower.phone;
		this.password = borrower.password;
		this.transactions = borrower.transactions;
		this.lateDays = borrower.lateDays;
	}
}
