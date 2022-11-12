package catalog.beans;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Entity
public class Borrower extends User {
	@Autowired
	@OneToMany
	Transaction transaction;
	int lateDays;
}
