package catalog.beans;

import javax.persistence.Entity;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Entity
public class Librarian extends Borrower {
	double salary;
	double hours;
	boolean isAdmin;
}
