package catalog.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import catalog.beans.Book;
import catalog.beans.Borrower;
import catalog.beans.InventoryItem;
import catalog.beans.Transaction;
import catalog.beans.User;
import catalog.repository.TransactionRepository;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 26, 2022
 */
@Service
public class TransactionService {
	@Autowired
	TransactionRepository txRepo;
	public TransactionService() {
		
	}
	
	public Transaction checkout(Borrower borrower, InventoryItem item) {
		Transaction tx = new Transaction(borrower, item);
		return null;
	}
	
	public Transaction newItem(Borrower borrower, InventoryItem item) {
		Transaction transaction = new Transaction();
		transaction.setOpen(false);
		transaction.setReturnDate(LocalDate.now());
		transaction = txRepo.save(transaction);
		return transaction;
	}
}
