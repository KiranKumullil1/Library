/*
 * Copyright (c) 2018. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.acrm.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rocks.process.acrm.data.domain.Book;
import rocks.process.acrm.data.domain.Librarian;
import rocks.process.acrm.data.repository.BookRepository;
import rocks.process.acrm.data.repository.LibrarianRepository;
import rocks.process.acrm.data.repository.ShelfRepository;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@Service
@Validated
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LibrarianService librarianService;
    @Autowired
    private ShelfService shelfService;

    public Book editBook(@Valid Book book) throws Exception {
        if (book.getBookId() == null) {
            if (bookRepository.findByTitle(book.getTitle()) == null) {
                if (book.getLibrarian() == null) {
                    book.setLibrarian(librarianService.getCurrentLibrarian());
                }
                return bookRepository.save(book);
            }
            throw new Exception("Book: " + book.getTitle() + " is already in the library.");
        }
        if (bookRepository.findByTitleAndBookIdNot(book.getTitle(), book.getBookId()) == null) {
            if (book.getLibrarian() == null) {
                book.setLibrarian(librarianService.getCurrentLibrarian());
            }
            return bookRepository.save(book);
        }
        throw new Exception("Book: " + book.getTitle() + " is already in the library.");
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> findBookByShelfId(Long shelfId) throws Exception {
        List<Book> bookList = bookRepository.findByShelf_ShelfId(shelfId);
        if(bookList.isEmpty()) {
            throw new Exception("No Animals are situated in this sector");
        }
        return bookList;
    }

    public Book findBookById(Long bookId) throws Exception {
        List<Book> bookList = bookRepository.findByBookIdAndLibrarian_LibrarianId(bookId, librarianService.getCurrentLibrarian().getLibrarianId());
        if(bookList.isEmpty()){
            throw new Exception("No Book with ID "+bookId+" found.");
        }
        return bookList.get(0);
    }

    public void deleteBook(Long bookId) { bookRepository.deleteById(bookId);}

    @PostConstruct
    private void init() throws Exception {
        if (bookRepository.findByTitle("Test") == null) {
            Book book = new Book();
            book.setTitle("test");
            book.setGenre("test");
            book.setPages("100");
            book.setAuthor("test");
            book.setShelf(shelfService.getShelf(4l));
            this.editBook(book);
        }
        if (bookRepository.findByTitle("Test") == null) {
            Book book = new Book();
            book.setTitle("test");
            book.setGenre("test");
            book.setPages("100");
            book.setAuthor("test");
            book.setShelf(shelfService.getShelf(4L));
            this.editBook(book);
        }
    }
    }
