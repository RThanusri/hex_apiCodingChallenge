package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Book;
import com.example.demo.Exception.BookAlreadyExistsException;
import com.example.demo.Exception.BookNotFoundException;
import com.example.demo.Service.BookService;

@RestController
@RequestMapping("/api/admin") 
public class BookController {
	
	@Autowired
	private BookService bookService;
	@PostMapping("/addBook")
	public ResponseEntity<Book> addBook(Book book)
	{
		try
		{
			Book b= bookService.addBook(book);
			return ResponseEntity.ok(b);
		}
		catch(IllegalArgumentException e)
		{
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		catch(BookAlreadyExistsException e)
		{
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/updateBook/{bookId}")
	public ResponseEntity<String>updateBook(@PathVariable long bookId,Book book)
	{
		try
		{
		   String b = bookService.updateBook(bookId,book);
			return ResponseEntity.ok(b);
		}
		
		catch(BookNotFoundException e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	

}
	@DeleteMapping("/deleteBookByIsbn/{isbn}")
	public ResponseEntity<String>deleteBook(@PathVariable String isbn)
	{
		try
		{
		   bookService.deleteBookByIsbn(isbn);
			return ResponseEntity.ok("Book deleted successfully");
		}
		
		catch(BookNotFoundException e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	
	
}
	@GetMapping("/getAllBooks")
	public ResponseEntity<List<Book>> getAllBooks()
	{
		
			List<Book> list= bookService.getAllBooks();
			if(list.isEmpty())
			{
				return ResponseEntity.noContent().build();
			}
			else
			{
			  return ResponseEntity.ok(list);
			}
		 
	
	
}
	@GetMapping("/getBookByIsbn/{isbn}")
	public ResponseEntity<Book>getBook(@PathVariable String isbn)
	{
		try
		{
			Book b=bookService.getBook(isbn);
			return ResponseEntity.ok(b);
			
		}
		catch(BookNotFoundException e)
		{
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	
	}
	
	
}