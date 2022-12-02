package catalog.controller;

import java.time.LocalDate;
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
import catalog.beans.Transaction;
import catalog.beans.User;
import net.bytebuddy.asm.Advice.Local;

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
	
	public InventoryController() {}
	
	//public void setInventoryService(InventoryService invService) {this.invService = invService;}
	
	@GetMapping({"/inv/viewAllItems", "/inv"})
	public String viewAllItems(Model model) {
		List<InventoryItem> items = invService.findAllItems();

		model.addAttribute("activeUser", UserService.getActiveUser());
		model.addAttribute("items", items);
		return "viewInventory.html";
	}
	
	@GetMapping("/inv/addNewItem/{bookISBN}")
	public String addNewItem(@PathVariable("bookISBN") String isbn, Model model){
		String addOrEdit = "Add";
		Book book = bookService.findByISBN(isbn);
		List<InventoryItem> copies = invService.findItemByISBN(isbn);
		
		model.addAttribute("addOrEdit", addOrEdit);
		model.addAttribute("book", book);
		model.addAttribute("copies", copies);
		
		return "invItemInfoForm";
	}
	
	@PostMapping("/inv/confirmNewItem")
	public String confirmNewItem(@ModelAttribute String isbn, Model model) {
		Book book = bookService.findByISBN(isbn);
		InventoryItem item = new InventoryItem(book);
		User activeUser = (User)model.getAttribute("activeUser");
		
		item = invService.saveItem(item);
		//Send to transaction controller
		
		//
		return "redirect:/tx/newItem/" + item.getId();
	}
	
	//No need to edit? add and delete only?
	@GetMapping("/inv/editItem/{id}")
	public String editItem(@PathVariable int id, Model model) {
		String addOrEdit = "Edit";
		
		InventoryItem item = invService.findItemById(id);
		
		model.addAttribute("addOrEdit", addOrEdit);
		//model.addAttribute("books", books);
		model.addAttribute("item", item);
		
		return "invItemInfoForm";
	}
	@GetMapping("/inv/itemDetail/{id}")
	public String itemDetail(@PathVariable int id, Model model) {
		InventoryItem item = invService.findItemById(id);
		model.addAttribute("item", item);
		return "itemDetail.html";
	}
}
