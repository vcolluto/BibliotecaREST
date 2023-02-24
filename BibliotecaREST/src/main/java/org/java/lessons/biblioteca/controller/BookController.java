package org.java.lessons.biblioteca.controller;

import java.util.List;
import java.util.Optional;

import org.java.lessons.biblioteca.model.Book;
import org.java.lessons.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	BookRepository bookRepository;

	@GetMapping()		//elenco dei libri. Richiesta GET /books
	public List<Book> index() {
		return bookRepository.findAll();
	}
	
	@GetMapping("{id}")		//dettaglio
	public ResponseEntity<Book> detail(@PathVariable("id") Integer id) {
		Optional<Book> res=bookRepository.findById(id);
		if (res.isPresent()) 
			return new ResponseEntity<Book>(res.get(), HttpStatus.OK);
	    else
	    	return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping()
	public Book create(@RequestBody Book book) {
		return bookRepository.save(book);
	}
	
	@PutMapping("{id}")	//aggiornamento   Richiesta PUT /books/xx
	public Book update(@RequestBody Book book,
			@PathVariable("id") Integer id) {
		Book b=bookRepository.getReferenceById(id);
		b.setTitle(book.getTitle());
		//....
		return bookRepository.save(b);
	}
	
	@DeleteMapping("{id}")	//eliminazione   Richiesta DELETE /books/xx
	public void delete(
			@PathVariable("id") Integer id) {
		bookRepository.deleteById(id);
	}
	
}
