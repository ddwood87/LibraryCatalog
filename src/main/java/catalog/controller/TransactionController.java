package catalog.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import catalog.beans.Borrower;
import catalog.beans.InventoryItem;
import catalog.beans.Transaction;
import catalog.beans.User;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 26, 2022
 */
@Controller
public class TransactionController {
	@Autowired
	TransactionService txService;
	@Autowired
	UserService userService;
	@Autowired
	InventoryService invService;
	
	public TransactionController() {}
	public TransactionController(TransactionService service, User user) {
		txService = service;
	}
	
	public void setTxService(TransactionService service) {
		this.txService = service;
	}
	
	@GetMapping("/tx/newItem/{itemId}")
	public String newItem(@PathVariable("itemId") int itemId, Model model) {
		InventoryItem item = invService.findItemById(itemId);
		User user = UserService.getActiveUser();
		String userClass = userService.getTypeName(user);
		if(userClass.equals("Borrower") || userClass.equals("Librarian")) {
			Transaction tx = txService.newItem((Borrower)user, item);
			item.addTransaction(tx);
		}
		return "/tx/viewTransactions";
		
	}
	public String checkOutItem(Model model) {
		model.addAttribute("activeUser", UserService.getActiveUser());
		return null;
	}
	
	//borrower uses returnItem.
	public String returnItem(Model model) {
		model.addAttribute("activeUser", UserService.getActiveUser());
		return null;
	}
	
	//librarian uses receiveItem to confirm return.
	public String receiveItem(Model model) {
		model.addAttribute("activeUser", UserService.getActiveUser());
		return null;
	}
}
