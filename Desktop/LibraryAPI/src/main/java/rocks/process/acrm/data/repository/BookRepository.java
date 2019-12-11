/*
 * Copyright (c) 2018. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.acrm.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocks.process.acrm.data.domain.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	Book findByTitle(String bookTitle);
	Book findByTitleAndBookIdNot(String bookTitle, Long BooklId);
	List<Book> findByShelf_ShelfId(Long shelfId);
	List<Book> findByBookIdAndLibrarian_LibrarianId(Long bookId, Long librarianId);
	List<Book> findAll();
}
