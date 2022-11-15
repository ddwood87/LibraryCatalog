package catalog;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import com.fasterxml.jackson.databind.ObjectMapper;

import catalog.beans.Librarian;
import catalog.beans.User;
import catalog.controller.UserController;
import catalog.repository.UserRepository;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 13, 2022
 */
@SpringBootTest
class TestUserController {
	
	MockMvc mockMvc;
	@Autowired
	UserRepository userRepo;
	@Autowired
	ObjectMapper objectMapper;
	Librarian admin;
	List<User> users;
	String[] fnames, lnames, phones;
	Model model;
	int count;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		count = 3;
		fnames = new String[count];
		lnames = new String[count];
		phones = new String[count];
		users = new ArrayList<User>();
		for(int i = 0; i < 3; i++) {
			fnames[i] = "FirstName" + (i+1);
			lnames[i] = "LastName" + (i+1);
			phones[i] = "123456789" + i;
			User u = new User();
			u.setFirstName(fnames[i]);
			u.setLastName(lnames[i]);
			u.setPhone(phones[i]);
			users.add(u);
		}
		admin = new Librarian();
		admin.setAdmin(true);
		mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userRepo, admin)).build();
	}
	void saveSomeUsers() {
		userRepo.saveAll(users);
	}
	void deleteSomeUsers() {
		userRepo.deleteAll(users);
	}
	int countUsers() {
		return userRepo.findAll().size();
	}
	int getFirstId() {
		Example<User> ex = Example.of(users.get(1));
		User u = userRepo.findOne(ex).orElse(null);
		return u.getId();
	}
	/**
	 * Test method for {@link catalog.controller.UserController#viewAllUsers(org.springframework.ui.Model)}.
	 * @throws Exception 
	 */
	@Test
	void testViewAllUsers() throws Exception {
		saveSomeUsers();
		
		mockMvc.perform(get("/users/viewAllUsers"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("users"))
			//.andExpect(model().attribute("users", users)) //Fails because Lists are different instances?
			.andExpect(view().name("viewUsers.html"));
		
		deleteSomeUsers();
	}

	/**
	 * Test method for {@link catalog.controller.UserController#addNewUser(org.springframework.ui.Model)}.
	 */
	@Test
	void testAddNewUser() throws Exception{
		mockMvc.perform(get("/users/addNewUser"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("addOrEdit", "Add"))
			.andExpect(view().name("userInfoForm.html"));
	}

	/**
	 * Test method for {@link catalog.controller.UserController#editUser(int, org.springframework.ui.Model)}.
	 */
	@Test
	void testEditUser() throws Exception{
		saveSomeUsers();
		
		//Get a valid id
		int id = getFirstId();
		//Get the user object with that id
		User u = userRepo.findById(id).orElse(null);
		
		mockMvc.perform(get("/users/editUser/" + id))
			.andExpect(status().isOk())
			.andExpect(model().attribute("addOrEdit", "Edit"))
			//This method won't match objects with like properties. Must check each property.
			.andExpect(model().attribute("user", Matchers.hasProperty("id", Matchers.equalTo(u.getId()))))
			.andExpect(model().attribute("user", Matchers.hasProperty("lastName", Matchers.equalTo(u.getLastName()))))
			.andExpect(model().attribute("user", Matchers.hasProperty("firstName", Matchers.equalTo(u.getFirstName()))))
			.andExpect(view().name("userInfoForm.html"));
		
		deleteSomeUsers();
	}

	/**
	 * Test method for {@link catalog.controller.UserController#deleteUser(int, org.springframework.ui.Model)}.
	 */
	@Test
	void testDeleteUser() throws Exception{
		saveSomeUsers();
		
		//Get a valid ID
		int id = getFirstId();
		
		//Assert number of users is test item count;
		assertTrue(countUsers() == count);
		
		mockMvc.perform(get("/users/deleteUser/" + id))
			.andExpect(status().isOk())
			.andExpect(view().name("viewUsers.html"));
		
		//Assert a user has been removed
		assertTrue(countUsers() == (count-1));
		
		deleteSomeUsers();
	}
	@Test
	void testCreateUser() throws Exception{
		
		User u = users.get(0);
		
		mockMvc.perform(post("/users/updateUser")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("firstName", u.getFirstName())
				.param("lastName", u.getLastName())
				.param("phone", u.getPhone()))
			.andExpect(status().isOk())
			.andExpect(view().name("userDetail.html"));
		User found = userRepo.findOne(Example.of(u)).orElse(null);
		assertTrue(found.equals(u));
	}
}
