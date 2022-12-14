package catalog.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

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
public class InventoryItem {
	
	@Id
	@GeneratedValue
	int id;
	@ManyToOne
	Book book;
	boolean checkedOut;
	@OneToMany
	@OrderBy("id DESC")
	List<Transaction> transactions;
	
	public InventoryItem(Book book) {
		this.book = book;
	}
	
	public void addTransaction(Transaction transaction) {
		if(transactions == null) {
			transactions = new ArrayList<Transaction>();
		}
		transactions.add(0, transaction);
	}
}
