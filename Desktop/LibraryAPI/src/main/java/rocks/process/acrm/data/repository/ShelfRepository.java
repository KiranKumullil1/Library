/*
 * Copyright (c) 2018. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.acrm.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocks.process.acrm.data.domain.Librarian;
import rocks.process.acrm.data.domain.Shelf;

import java.util.List;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {
	 Shelf findByShelfId(Long shelfId);
	Shelf findByShelfName(String shelfName);
	Shelf findByShelfNameAndShelfIdNot(String shelfName, Long shelfId);
	List<Shelf> findAll();
}
