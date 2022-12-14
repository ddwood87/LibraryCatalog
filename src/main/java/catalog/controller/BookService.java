package catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import catalog.beans.Book;
import catalog.repository.BookRepository;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 25, 2022
 */
@Service
public class BookService {
	
	@Autowired
	BookRepository bookRepo;
	
	public BookService(BookRepository bookRepo) {
		this.bookRepo = bookRepo;
	}
	
	public List<Book> findAllBooks(){
		return bookRepo.findAll();
	}

	public Book findByISBN(String isbn) {
		return bookRepo.findById(isbn).orElse(null);
	}
	
	public Book saveBook(Book book) {
		return bookRepo.save(book);
	}

	public void deleteByISBN(String isbn) {
		bookRepo.deleteById(isbn);
	}
}
