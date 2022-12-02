package catalog.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Getter
@Setter
@Entity
public class Borrower extends User {
	@OneToMany(mappedBy="borrower")
	List<Transaction> transactions;
	String password;
	
	int lateDays;
}
