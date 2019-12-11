/*
 * Copyright (c) 2018. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.acrm.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocks.process.acrm.data.domain.Librarian;

import java.util.List;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
	Librarian findByLibrarianId(Long librarianId);
	 Librarian findByEmail(String email);
	Librarian findByEmailAndLibrarianIdNot(String email, Long librarianId);
	List<Librarian> findAll();
}
