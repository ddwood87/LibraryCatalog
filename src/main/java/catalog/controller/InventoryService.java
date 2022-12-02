package catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import catalog.beans.Book;
import catalog.beans.InventoryItem;
import catalog.repository.InventoryRepository;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 26, 2022
 */
@Service
public class InventoryService {
	@Autowired
	InventoryRepository invRepo;
	
	public InventoryService() {}
	
	public List<InventoryItem> findAllItems() {
		return invRepo.findAll();
	}
	
	public InventoryItem saveItem(InventoryItem item) {
		return invRepo.save(item);
	}
	
	public InventoryItem getItem(Book book) {
		InventoryItem exItem = new InventoryItem();
		exItem.setBook(book);
		Example<InventoryItem> ex = Example.of(exItem);
		return invRepo.findOne(ex).orElse(null);
	}
	
	public InventoryItem findItemById(int id) {
		return invRepo.findById(id).orElse(null);
	}
	
	public void deleteItem(InventoryItem item) {
		invRepo.delete(item);
	}

	public List<InventoryItem> findItemByISBN(String isbn) {
		Book b = new Book();
		b.setIsbn(isbn);
		InventoryItem item = new InventoryItem();
		item.setBook(b);
		Example<InventoryItem> example = Example.of(item);
		List<InventoryItem> items = invRepo.findAll(example);
		return items;
	}
	
}
