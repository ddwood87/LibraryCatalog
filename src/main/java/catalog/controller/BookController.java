package catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import catalog.beans.Book;
import catalog.beans.User;
import catalog.repository.BookRepository;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 19, 2022
 */
@Controller
public class BookController {
	@Autowired
	BookRepository bookRepo;
	@Autowired
	User activeUser;
	
	//All users can currently access book controller
	public BookController() {}
	public BookController(BookRepository repo, User user) {
		activeUser = user;
		bookRepo = repo;
	}
	
	public void setBookRepo(BookRepository bookRepository) {bookRepo = bookRepository;}
	
	@GetMapping({"/books/viewAllBooks", "/"}) //Remove "/" URL path. For development only.
	public String viewAllBooks(Model model) {
		List<Book> books = bookRepo.findAll();
		if(books.isEmpty()) {
			return addNewBook(model);
		}
		model.addAttribute("books", books);
		return "viewUsers.html";
	}
	
	public String userInput(Model model) {
		String addOrEdit = (String)model.getAttribute("addOrEdit");
		if(addOrEdit == "Add") {	
			
		}else if(addOrEdit == "Edit") {
			
		}
		model.addAttribute("categories", Book.CATEGORIES);
		return "bookInfoForm.html";
	}
	
	@GetMapping({"/books/addNewBook"})	
	private String addNewBook(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		model.addAttribute("addOrEdit", "Add");
		return userInput(model);
	}
	
	@GetMapping("/books/editBook/{id}")
	private String editBook(@PathVariable("id") String isbn, Model model) {
		model.addAttribute("addOrEdit", "Edit");
		Book book = bookRepo.findById(isbn).orElse(null);
		if(book != null) {
			model.addAttribute("book", book);
		}
		return userInput(model);
	}
	
	@PostMapping("/books/updateBook")
	public String updateBook(@ModelAttribute Book book, Model model) {
		book = bookRepo.save(book);
		model.addAttribute("book", book);
		return bookDetail(book.getIsbn(), model);
	}
	
	@GetMapping("/books/deleteUser/{isbn}")
	public String deleteBook(@PathVariable("isbn") String isbn, Model model) {
		bookRepo.deleteById(isbn);
		return viewAllBooks(model);
	}
	
	@GetMapping("/books/bookDetail/{isbn}")
	public String bookDetail(@PathVariable String isbn, Model model) {
		Book b = bookRepo.findById(isbn).orElse(null);
		model.addAttribute("book", b);
		return "bookDetail.html";
	}
}
