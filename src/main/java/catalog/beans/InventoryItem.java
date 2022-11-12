package catalog.beans;

import java.util.List;

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
public class InventoryItem {
	@Id
	@GeneratedValue
	int id;
	@Autowired
	@ManyToOne
	Book book;
	boolean checkedOut;
	@Autowired
	Borrower borrower;
	
	List<Transaction> transactions;
}
