package catalog.controller;

import java.time.LocalDate;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import catalog.beans.Borrower;
import catalog.beans.InventoryItem;
import catalog.beans.Librarian;
import catalog.beans.Transaction;
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
	EntityManagerFactory emfactory;
	public TransactionService() {
		
	}
	
	//public List<Transaction> getTransactions(){}
	//public List<Transaction> getTransactions(TxFilter filter) {}
	public Transaction checkout(Borrower borrower, InventoryItem item) {
		Transaction tx = new Transaction(borrower, item);
		item.setCheckedOut(true);
		item.addTransaction(tx);
		borrower.addTransaction(tx);
		tx = txRepo.save(tx);
		return tx;
	}
	
	public Transaction checkIn(Transaction tx) {
		tx.setOpen(false);
		tx.setReturnDate(LocalDate.now());
		InventoryItem item = tx.getItem();
		item.setCheckedOut(false);
		tx = txRepo.save(tx);
		return tx;
	}
	public Transaction newItem(Librarian librarian, InventoryItem item) {
		Transaction transaction = new Transaction();
		transaction.setBorrower(librarian);
		transaction.setOpen(false);
		transaction.setReturnDate(LocalDate.now());
		transaction.setItem(item);
		transaction = txRepo.save(transaction);
		return transaction;
	}

	public Transaction saveTx(Transaction tx) {
		return txRepo.save(tx);
	}

	public Transaction findTransactionById(int id) {
		return txRepo.findById(id).orElse(null);
	}
}
