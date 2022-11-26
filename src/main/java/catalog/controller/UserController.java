package catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import catalog.beans.User;
import catalog.repository.UserRepository;
import ch.qos.logback.core.Context;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Controller
public class UserController {
	@Autowired
	UserRepository userRepo;	
	@Autowired
	User activeUser;
	
	public UserController() {}
	public UserController(UserRepository repo, User user) {
		activeUser = user;
		userRepo = repo;
	}
	
	public void setUserRepo(UserRepository userRepository) { userRepo = userRepository; }
	
	@GetMapping({"/users/viewAllUsers", "/users"})
	public String viewAllUsers(Model model) {
		if(userRepo.findAll().isEmpty()) {
			return addNewUser(model);
		}
		model.addAttribute("users", userRepo.findAll());
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
		User user = userRepo.findById(id).orElse(null);
		if(user != null) {
			model.addAttribute("user", user);
		}
		return userInput(model);
	}
	
	@PostMapping("/users/updateUser")
	public String updateUser(@ModelAttribute User user, Model model) {
		
		user = userRepo.save(user);
		model.addAttribute("user", user);
		
		return userDetail(user.getId(), model);
	}
	
	//delete
	@GetMapping("/users/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") int id, Model model) {
		//Check for Librarian user class w/ isAdmin true.
		
		//Redirect to confirm delete?
		
		userRepo.deleteById(id);
		return viewAllUsers(model);
	}
	
	@GetMapping("/users/userDetail/{id}")
	public String userDetail(@PathVariable int id, Model model) {
		User u = userRepo.findById(id).orElse(null);
		Class<? extends User> classType = u.getClass();
		model.addAttribute("userClass", classType.getTypeName());
		model.addAttribute("user", u);
		return "userDetail.html";
	}
}
