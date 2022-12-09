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
	BookService bookService;
	@Autowired
	UserService userService;
	
	//All users can currently access book controller
	public BookController() {}
	public BookController(BookService service) {
		bookService = service;
	}
	
	public void setBookService(BookService bookService) {this.bookService = bookService;}
	
	@GetMapping({"/books/viewAllBooks", "/books"})
	public String viewAllBooks(Model model) {
		List<Book> books = bookService.findAllBooks();
		
		if(books.isEmpty()) {
			return addNewBook(model);
		}
		model.addAttribute("activeUser", userService.getActiveUser());
		model.addAttribute("books", books);
		return "viewBooks.html";
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
	public String addNewBook(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		model.addAttribute("addOrEdit", "Add");
		return userInput(model);
	}
	
	@GetMapping("/books/editBook/{isbn}")
	public String editBook(@PathVariable("isbn") String isbn, Model model) {
		model.addAttribute("addOrEdit", "Edit");
		Book book = bookService.findByISBN(isbn);
		if(book != null) {
			model.addAttribute("book", book);
		}
		return userInput(model);
	}
	
	@PostMapping("/books/updateBook")
	public String updateBook(@ModelAttribute Book book, Model model) {
		book = bookService.saveBook(book);
		model.addAttribute("book", book);
		return bookDetail(book.getIsbn(), model);
	}
	
	@GetMapping("/books/deleteBook/{isbn}")
	public String deleteBook(@PathVariable("isbn") String isbn, Model model) {
		bookService.deleteByISBN(isbn);
		return viewAllBooks(model);
	}
	
	@GetMapping("/books/bookDetail/{isbn}")
	public String bookDetail(@PathVariable String isbn, Model model) {
		Book b = bookService.findByISBN(isbn);
		model.addAttribute("book", b);
		return "bookDetail.html";
	}
}
