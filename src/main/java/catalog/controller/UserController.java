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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		model.addAttribute("addOrEdit", "Edit");
		User user = userService.findUserById(id);
		if(user != null) {
			model.addAttribute("user", user);
		}
		return userInput(model);
	}
	
	@PostMapping("/users/updateUser")
	public String updateUser(@ModelAttribute User user, Model model) {
		
		user = userService.saveUser(user);
		model.addAttribute("user", user);
		
		return userDetail(user.getId(), model);
	}
	
	//delete
	@GetMapping("/users/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") int id, Model model) {
		//Check for Librarian user class w/ isAdmin true.
		
		//Redirect to confirm delete?
		User user = userService.findUserById(id);
		userService.deleteUser(user);
		return viewAllUsers(model);
	}
	
	@GetMapping("/users/userDetail/{id}")
	public String userDetail(@PathVariable int id, Model model) {
		User u = userService.findUserById(id);
		Class<? extends User> classType = u.getClass();
		model.addAttribute("userClass", classType.getTypeName());
		model.addAttribute("user", u);
		return "userDetail.html";
	}
	@GetMapping("/users/login")
	public String login(Model model) {
		return "login.html";
	}
	@PostMapping("/users/login")
	public String login(
			@ModelAttribute("id")String id, 
			@ModelAttribute("password")String password, 
			Model model) {
		User user = userService.loginUser(id, password);
		model.addAttribute("activeUser", UserService.getActiveUser());
		return userDetail(user.getId(), model);
	}
}
