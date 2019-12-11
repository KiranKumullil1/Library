/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.acrm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rocks.process.acrm.business.service.BookService;
import rocks.process.acrm.business.service.LibrarianService;
import rocks.process.acrm.business.service.ShelfService;
import rocks.process.acrm.data.domain.Book;
import rocks.process.acrm.data.domain.Librarian;
import rocks.process.acrm.data.domain.Shelf;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class Library {
    @Autowired
    private LibrarianService librarianService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ShelfService shelfService;


    //Create a new book
    @PostMapping(path = "/book", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Book> postBook(@RequestBody Book book) {
        try {
            book = bookService.editBook(book);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getConstraintViolations().iterator().next().getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{customerId}")
                .buildAndExpand(book.getBookId()).toUri();

        return ResponseEntity.created(location).body(book);
    }

    @PostMapping(path = "/librarian", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Librarian> registerLibrarian(@RequestBody Librarian librarian) {
        try {
            librarianService.saveLibrarian(librarian);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getConstraintViolations().iterator().next().getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{librarianId}")
                .buildAndExpand(librarian.getLibrarianId()).toUri();

        return ResponseEntity.created(location).body(librarian);
    }

    //Get a List of all books
    @GetMapping(path = "/book", produces = "application/json")
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    //Get a List of all librarian
    @GetMapping(path = "/librarian", produces = "application/json")
    public List<Librarian> getAllLibrarians() {
        return librarianService.getAllLibrarians();
    }

    //Get a List of all shelfs
    @GetMapping(path = "/shelf", produces = "application/json")
    public List<Shelf> getAllShelfs() {
        return shelfService.getAllShelfs();
    }

    //Get a specific book
    @GetMapping(path = "/book/{bookId}", produces = "application/json")
    public ResponseEntity<Book> getSingleBook(@PathVariable(value = "bookId") String bookId) {
        Book book = null;
        try {
            book = bookService.findBookById(Long.parseLong(bookId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(book);
    }

    //Todo this doesnt work anymore


    //Get all animals that are situated in a specific sector
    @GetMapping(path = "/{shelfId}", produces = "application/json")
    public List<Book> getAllBooksInShelf(@PathVariable(value = "shelfId") String shelfId) {
        List<Book> bookList = null;
        try {
            bookList = bookService.findBookByShelfId(Long.parseLong(shelfId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return bookList;
    }

    //Get a specific Sector to see its attributes
    @GetMapping(path = "/shelf/{shelfId}", produces = "application/json")
    public ResponseEntity<Shelf> getShelf(@PathVariable(value = "shelfId") String shelfId) {
        Shelf shelf = null;
        try {
            shelf = shelfService.getShelf(Long.parseLong(shelfId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok(shelf);
    }

    //I wanted to try to alter data without giving the whole object as parameter like i would in PUT
    //librarianId is wrongly used here i think, but it works. With more time and experience I would find a more elegant solution here
    @PatchMapping(path = "/book/{bookId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Book> patchBook(@PathVariable("bookId") String bookId, @RequestBody String shelfId, String librarianId) {
        Book book = null;
        try {
            book = bookService.findBookById(Long.parseLong(bookId));
            book.setShelf(shelfService.getShelf(Long.parseLong(shelfId)));
            book.setLibrarian(librarianService.getLibrarian(Long.parseLong(librarianId)));
            book = bookService.editBook(book);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.accepted().body(book);
    }

    //Delete an book
    @DeleteMapping(path = "/book/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable(value = "bookId") String bookId) {
        try {
            //TODO delete customer
            bookService.deleteBook(Long.parseLong(bookId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return ResponseEntity.accepted().build();
    }
}