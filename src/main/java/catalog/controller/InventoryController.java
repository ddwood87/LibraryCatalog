package catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import catalog.beans.Book;
import catalog.beans.Borrower;
import catalog.beans.InventoryItem;
import catalog.beans.Librarian;
import catalog.beans.Transaction;
import catalog.beans.User;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 26, 2022
 */
@Controller
public class InventoryController {
	@Autowired
	private InventoryService invService;
	@Autowired
	private BookService bookService;
	@Autowired
	private TransactionService txService;
	@Autowired
	private UserService userService;
	
	public InventoryController() {}
	
	//public void setInventoryService(InventoryService invService) {this.invService = invService;}
	
	@GetMapping({"/inv/viewAllItems", "/inv"})
	public String viewAllItems(Model model) {
		List<InventoryItem> items = invService.findAllItems();
		User activeUser = userService.getActiveUser();
		if(activeUser != null) {
			model.addAttribute("activeUser", activeUser);
			model.addAttribute("items", items);
			return "viewInventory.html";
		} else {return "redirect:/users/login";}
	}
	
	@GetMapping("/inv/addNewItem/{bookISBN}")
	public String addNewItem(@PathVariable("bookISBN") String isbn, Model model){
		String addOrEdit = "Add";
		Book book = bookService.findByISBN(isbn);
		List<InventoryItem> copies = invService.findItemByISBN(isbn);
		User activeUser = userService.getActiveUser();
		if(activeUser != null) {
			model.addAttribute("activeUser", activeUser);
			model.addAttribute("addOrEdit", addOrEdit);
			model.addAttribute("book", book);
			model.addAttribute("copies", copies);
			return "invItemInfoForm";
		} else {return "redirect:/users/login";}
	}
	
	@PostMapping("/inv/confirmNewItem")
	public String confirmNewItem(@ModelAttribute("isbn") String isbn, Model model) {
		Book book = bookService.findByISBN(isbn);
		InventoryItem item = new InventoryItem(book);
		User activeUser = userService.getActiveUser();
		if(activeUser != null && activeUser.getClass().equals(Librarian.class)){
			Librarian l = (Librarian)activeUser;
			Transaction tx = txService.newItem(l, item);
			l.addTransaction(tx);
			item.addTransaction(tx);
			activeUser = userService.saveUser(l);
			item = invService.saveItem(item);
		} else {return "redirect:/users/login";}
		
		return viewAllItems(model);
	}
	
	//No need to edit? add and delete only?
	@GetMapping("/inv/editItem/{id}")
	public String editItem(@PathVariable int id, Model model) {
		String addOrEdit = "Edit";
		InventoryItem item = invService.findItemById(id);
		User activeUser = userService.getActiveUser();
		
		if(activeUser != null){
			model.addAttribute("activeUser", activeUser);
			model.addAttribute("addOrEdit", addOrEdit);
			model.addAttribute("item", item);
		} else {return "/users/login";}
		
		return "invItemInfoForm";
	}
	@GetMapping("/inv/itemDetail/{id}")
	public String itemDetail(@PathVariable int id, Model model) {
		InventoryItem item = invService.findItemById(id);
		User activeUser = userService.getActiveUser();
		boolean hasHistory = false;
		if(activeUser != null){
			if(!item.getTransactions().isEmpty()) {
				hasHistory = true;
			}
			model.addAttribute("hasHistory", hasHistory);
			model.addAttribute("item", item);
			model.addAttribute("activeUser", userService.getActiveUser());
			return "invItemDetail.html";
		} else {return "redirect:/users/login";}
	}
	@GetMapping("/inv/checkOutItem/{id}")
	public String checkOutItem(@PathVariable("id") int id, Model model) {
		InventoryItem item = invService.findItemById(id);
		User activeUser = userService.getActiveUser();
		if(activeUser != null) {
			if( Borrower.class.isAssignableFrom(activeUser.getClass())) {
				if(item.isCheckedOut()) {
					return itemDetail(item.getId(), model);
				}else {						
					Borrower b = (Borrower)activeUser;
					Transaction tx = txService.checkout(b, item);
					b = tx.getBorrower();
					item = tx.getItem();
					activeUser = userService.saveUser(b);
					item = invService.saveItem(item);
				}
			}
		} else {return "redirect:/users/login";}
		return itemDetail(item.getId(), model);
	}
	@GetMapping("/inv/checkInItem/{id}")
	public String checkInItem(@PathVariable("id") int id, Model model) {
		Transaction tx = txService.findTransactionById(id);
		tx = txService.checkIn(tx);
		InventoryItem item = invService.saveItem(tx.getItem());
		return itemDetail(item.getId(), model);
	}
}
