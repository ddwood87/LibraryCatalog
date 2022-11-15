package catalog.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Entity
public class Book {
	@Id
	int ISBN;
	int title;
	@ElementCollection
	List<String> authors;
	String category;
	final static List<String> CATEGORIES = new ArrayList<String>() {
		{	
			add("Adventure"); 
			add("Sci-Fi");
			add("Education");  //Add more categories.
			add("Biography");
			add("Other");
		}
	};
}
