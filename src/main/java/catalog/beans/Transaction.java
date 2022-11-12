package catalog.beans;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Entity
public class Transaction {
	@Id
	@GeneratedValue
	int id;
	boolean isOpen;
	@Autowired
	@ManyToOne
	InventoryItem item;
	@Autowired
	@ManyToOne
	Borrower borrower;
	Date borrowDate;
	Date dueDate;
	Date returnDate;
}
