package rocks.process.acrm.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rocks.process.acrm.data.domain.Librarian;
import rocks.process.acrm.data.repository.LibrarianRepository;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@Service
@Validated
public class LibrarianService {

    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    Validator validator;

    public void saveLibrarian(@Valid Librarian librarian) throws Exception {
        if (librarian.getLibrarianId() == null) {
            if (librarianRepository.findByEmail(librarian.getEmail()) != null) {
                throw new Exception("Email address " + librarian.getEmail() + " already assigned another book.");
            }
        } else if (librarianRepository.findByEmailAndLibrarianIdNot(librarian.getEmail(), librarian.getLibrarianId()) != null) {
            throw new Exception("Email address " + librarian.getEmail() + " already assigned another book.");
        }
        librarianRepository.save(librarian);
    }

    public Librarian getLibrarian(Long librarianId) {
        return librarianRepository.findByLibrarianId(librarianId);
    }

    public  Librarian getCurrentLibrarian() {
        String userEmail = "test@test.ch";
        return librarianRepository.findByEmail(userEmail);
    }

    public List<Librarian> getAllLibrarians() {
        return librarianRepository.findAll();
    }

    @PostConstruct
    private void init() throws Exception {
        if (librarianRepository.findAll().size() < 5) {
            Librarian librarian = new Librarian();
            librarian.setName("Test");
            librarian.setEmail("test@test.ch");
            librarian.setPassword("test");
            librarian.setRemember("default");
            this.saveLibrarian(librarian);

            Librarian librarian2 = new Librarian();
            librarian2.setName("Test2");
            librarian2.setEmail("test2@test.ch");
            librarian2.setPassword("test2");
            librarian2.setRemember("default");
            this.saveLibrarian(librarian2);
        }

    }
}
