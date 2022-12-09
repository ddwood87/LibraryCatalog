package catalog.controller;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import catalog.beans.Borrower;
import catalog.beans.Librarian;
import catalog.beans.User;
import catalog.repository.UserRepository;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	public UserController() {}
	public UserController(UserService service) {
		userService = service;
	}
	
	public void setUserRepo(UserService userService) { this.userService = userService; }
	
	@GetMapping({"/users/viewAllUsers", "/users"})
	public String viewAllUsers(Model model) {
		if(userService.findAllUsers().isEmpty()) {
			return addNewUser(model);
		}
		List<User> users = userService.findAllUsers();
		Dictionary<Integer, String> userClasses = new Hashtable<Integer, String>();
		for(User u : users) {
			String type = userService.getTypeName(u);
			userClasses.put(u.getId(), type);
		}
		User activeUser = userService.getActiveUser();
		if(activeUser == null) {
			return login(model);
		}
		model.addAttribute("activeUser", activeUser);
		model.addAttribute("users", users);
		model.addAttribute("userClasses", userClasses);
		return "viewUsers.html";
	}
	//@GetMapping("/users/userInput") Not reachable by URL
	public String userInput(Model model) {
		String addOrEdit = (String)model.getAttribute("addOrEdit");
		if(addOrEdit == "Add") {	
			
		}else if(addOrEdit == "Edit") {
			
		}

		model.addAttribute("activeUser", userService.getActiveUser());
		return "userInfoForm.html";
	}
	//create
	@GetMapping({"/users/addNewUser"})	
	public String addNewUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("addOrEdit", "Add");
		return userInput(model);
	}
	//edit
	@GetMapping("/users/editUser/{id}")
	public String editUser(@PathVariable("id") int id, Model model) {
		User user = userService.findUserById(id);
		if(user != null) {
			model.addAttribute("user", user);
		}
		String userClass = userService.getTypeName(user);
		model.addAttribute("userClass", userClass);
		model.addAttribute("addOrEdit", "Edit");
		return userInput(model);
	}
	
	@PostMapping("/users/updateUser")
	public String updateUser(@ModelAttribute User user, @RequestParam("userClass") String userClass, Model model) {
		//Check for existing user.
		if(userService.userExists(user)) {
			//Check for class change
			if(!userService.getTypeName(user).equals(userClass)) {
				//Create selected class and transfer
				if(userClass.equals("Borrower")) {
					user = new Borrower(user);
				}else if(userClass.equals("Librarian") || userClass.equals("Admin")) {
					user = new Librarian(user);
					if(userClass.equals("Admin")) {
						((Librarian)user).setAdmin(true);
					}
				}
			}
		}else {
			if(userClass.equals("Borrower")) {
				Borrower b = new Borrower(user);
				user = b;
			}else if(userClass.equals("Librarian") || userClass.equals("Admin")) {
				Librarian l = new Librarian(user);
				if(userClass.equals("Admin")) {
					l.setAdmin(true);
				}
				user = l;
			}
		}
		user = userService.saveUser(user);
		model.addAttribute("user", user);
		
		return login(model);
	}
	
	//delete
	@GetMapping("/users/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") int id, Model model) {
		//Check for Librarian user class w/ isAdmin true.
		User activeUser = userService.getActiveUser();
		if(userService.getTypeName(activeUser).equals("Librarian") && ((Librarian)activeUser).isAdmin()) {
			//Redirect to confirm delete?
			User user = userService.findUserById(id);
			userService.deleteUser(user);
		}
		else {
			//message user about admin access
		}
		return viewAllUsers(model);
	}
	
	@GetMapping("/users/userDetail/{id}")
	public String userDetail(@PathVariable int id, Model model) {
		User u = userService.findUserById(id);
		String userClass = userService.getTypeName(u);
		User activeUser = userService.getActiveUser();
		
		if(u.equals(activeUser) || (userService.getTypeName(activeUser).equals("Librarian") && ((Librarian)activeUser).isAdmin())) {
			model.addAttribute("editor", true);
		} else {
			model.addAttribute("editor", false);
		}
		model.addAttribute("userClass", userClass);
		model.addAttribute("user", u);
		model.addAttribute("activeUser", activeUser);
		return "userDetail.html";
	}
	@GetMapping({"/users/login", "/index"})
	public String login(Model model) {
		return "login.html";
	}
	@PostMapping("/users/login")
	public String login(
			@ModelAttribute("username")String username, 
			@ModelAttribute("password")String password, 
			Model model) {
		User user = userService.loginUser(username, password);
		if(user == null) {
			model.addAttribute("errorMessage", "Phone # or password is incorrect.");
			return login(model);
		}
		return userDetail(user.getId(), model);
	}
}
