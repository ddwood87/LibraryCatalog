package catalog.beans;

import javax.persistence.Entity;

import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Entity
@Setter
@NoArgsConstructor
public class Librarian extends Borrower {
	double salary;
	double hours;
	boolean isAdmin;
}
