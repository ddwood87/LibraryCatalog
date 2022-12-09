package catalog.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("1")
public class Borrower extends User {
	
	@OneToMany(mappedBy="borrower")
	List<Transaction> transactions;
	int lateDays;
	
	/**
	 * @param user
	 */
	public Borrower(User user) {
		this.id = user.id;
		this.userName = user.userName;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.phone = user.phone;
		this.password = user.password;
	}
	public Borrower(Librarian librarian) {
		this.id = librarian.id;
		this.userName = librarian.userName;
		this.firstName = librarian.firstName;
		this.lastName = librarian.lastName;
		this.phone = librarian.phone;
		this.password = librarian.password;
		this.transactions = librarian.transactions;
		this.lateDays = librarian.lateDays;
	}
	/**
	 * @param tx
	 */
	public void addTransaction(Transaction transaction) {
		if(transactions == null) {
			transactions = new ArrayList<Transaction>();
		}
		transactions.add(transaction);
	}
}
