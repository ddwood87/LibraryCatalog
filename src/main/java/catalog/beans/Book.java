package catalog.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 11, 2022
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Book {
	@Id
	String isbn;
	String title;
	@ElementCollection
	List<String> authors;
	String category;
	public final static List<String> CATEGORIES = new ArrayList<String>() {
		{	
			add("Adventure"); 
			add("Sci-Fi");
			add("Education");  //Add more categories.
			add("Biography");
			add("Other");
		}
	};
	
	public Book() {
		this.authors = new ArrayList<String>();
	}
}
