package catalog.beans;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Transaction {
	
	public Transaction(Borrower borrower, InventoryItem item) {
		this.open = true;
		this.item = item;
		this.borrower = borrower;
		this.borrowDate = LocalDate.now();
		this.dueDate = borrowDate.plusDays(DAYS_TO_BORROW);
	}
	
	@Id
	@GeneratedValue
	int id;
	boolean open;
	@ManyToOne
	InventoryItem item;
	@ManyToOne
	Borrower borrower;
	LocalDate borrowDate;
	LocalDate dueDate;
	LocalDate returnDate;
	
	static int DAYS_TO_BORROW = 14;
}
