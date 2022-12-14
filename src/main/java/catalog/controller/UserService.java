package catalog.controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

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
	@PersistenceContext
	private EntityManager em;
	private static int activeUser;
	public UserService(){
		super();
	}
	
	public User getActiveUser() {
		if(activeUser == 0) {
			return null;
		}else {			
			return findUserById(activeUser);
		}
	}

	public List<User> findAllUsers() {
		List<User> list = userRepo.findAll();
		return list;
	}

	public User findUserById(int id) {
		User user = userRepo.findById(id).orElse(null);
		return user;
	}

	public User saveUser(User user) {	
		if(userExists(user)) {
			User u = findUserById(user.getId());
			if(getTypeName(user).equals(getTypeName(u))){
				user = userRepo.save(user);
			}else {
				deleteUser(u);
				user = userRepo.save(user);
			}
		}else {
			user = userRepo.save(user);
		}
		
		return user;
	}

	public void deleteUser(User user) {
		userRepo.delete(user);
	}

	public User loginUser(String username, String password) {
		User user = findUserByUsername(username);		
		if(user.checkPassword(password)) {
			activeUser = user.getId();
		} else { return null; }
		return user;
	}
	
	public void logoutActiveUser() {
		activeUser = 0;
		
	}
	
	private User findUserByUsername(String username) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class);
		query.setParameter("username", username);
		User u = query.getSingleResult();
		return u;
	}

	public void saveAllUsers(List<User> users) {
		userRepo.saveAll(users);
	}

	public void deleteAllUsers(List<User> users) {
		userRepo.deleteAll(users);
	}

	
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
	
	public boolean userExists(User user) {
		return userRepo.existsById(user.getId());
	}

}
