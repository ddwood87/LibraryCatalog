package catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import catalog.beans.Borrower;
import catalog.beans.User;
import catalog.repository.UserRepository;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Dec 1, 2022
 */
@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	private static User activeUser;
	public static User getActiveUser() {
		return activeUser;
	}
	/**
	 * @return
	 */
	public List<User> findAllUsers() {
		List<User> list = userRepo.findAll();
		return list;
	}

	/**
	 * @param id
	 * @return
	 */
	public User findUserById(int id) {
		User user = userRepo.findById(id).orElse(null);
		return user;
	}

	/**
	 * @param user
	 * @return
	 */
	public User saveUser(User user) {
		user = userRepo.save(user);
		return user;
	}

	/**
	 * @param id
	 */
	public void deleteUser(User user) {
		userRepo.delete(user);
	}

	/**
	 * @param id
	 * @param password
	 */
	public User loginUser(String id, String password) {
		int intId = Integer.parseInt(id);
		User user = findUserById(intId);
		String typeName = getTypeName(user);
		
		if(!typeName.equals("User")) {
			Borrower borrower = (Borrower)user;
			if(borrower.getPassword() == password) {
				activeUser = borrower;
				return borrower;
			}
			else { return null; }
		}
		else { 
			activeUser = user;
			return user; 
		}
		
	}
	
	/**
	 * @param users
	 */
	public void saveAllUsers(List<User> users) {
		userRepo.saveAll(users);
	}

	/**
	 * @param users
	 */
	public void deleteAllUsers(List<User> users) {
		userRepo.deleteAll(users);
	}

	/**
	 * @param ex
	 * @return
	 */
	public User findOneUser(Example<User> ex) {
		User user = userRepo.findOne(ex).orElse(null);
		return user;
	}
	public String getTypeName(User user) {
		String longType = user.getClass().getTypeName();
		String[] longTypeSplit = longType.split("\\.");
		String type = longTypeSplit[longTypeSplit.length - 1];
		return type;
	}
}
