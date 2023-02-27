package org.java.lessons.biblioteca.controller;

import java.util.List;
import java.util.Optional;

import org.java.lessons.biblioteca.model.Book;
import org.java.lessons.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	BookRepository bookRepository;

	@GetMapping()		//elenco dei libri. Richiesta GET /books
	public ResponseEntity<List<Book>> index(
		@RequestParam(name = "keyword", required = false) String keyword) {
		
		List<Book> res;
		if (keyword!=null && !keyword.isEmpty()) 
			res= bookRepository.findByTitleLikeOrderByTitle("%"+keyword+"%");
		else 
			res= bookRepository.findAll();
		if (res.size()==0)
			return new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);	//non ci sono dati, ma la risposta è OK
		else
			return new ResponseEntity<List<Book>>(res, HttpStatus.OK);
		
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
	public ResponseEntity<Book> create(@Valid @RequestBody Book book) {
		return new ResponseEntity<Book>( bookRepository.save(book), HttpStatus.OK);
	}
	
	@PutMapping("{id}")	//aggiornamento   Richiesta PUT /books/xx
	public ResponseEntity<Book> update(@Valid @RequestBody Book book,
			@PathVariable("id") Integer id) {
		Optional<Book> res=bookRepository.findById(id);
		
		if (res.isPresent()) {			
			bookRepository.save(book);
			return new ResponseEntity<Book>( book, HttpStatus.OK);
		}
			
	    else
	    	return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping("{id}")	//eliminazione   Richiesta DELETE /books/xx
	public ResponseEntity<Book> delete(
			@PathVariable("id") Integer id) {		
		Optional<Book> res=bookRepository.findById(id);		
		if (res.isPresent()) {			
			bookRepository.deleteById(id);
			return new ResponseEntity<Book>( HttpStatus.OK);
		}			
	    else
	    	return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		
	}
	
}
