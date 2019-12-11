package rocks.process.acrm.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;
import rocks.process.acrm.data.domain.Shelf;
import rocks.process.acrm.data.repository.ShelfRepository;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import org.springframework.validation.Validator;

@Service
@Validated
public class ShelfService {

    @Autowired
    private ShelfRepository shelfRepository;

    // @Autowired
    Validator validator;

    public void saveShelf(@Valid Shelf shelf) throws Exception {
        if (shelf.getShelfId() == null) {
            if (shelfRepository.findByShelfName(shelf.getShelfName()) != null) {
                throw new Exception("Sector Name: " + shelf.getShelfName() + " is already assigned to another Sector");
            }
        } else if (shelfRepository.findByShelfNameAndShelfIdNot(shelf.getShelfName(), shelf.getShelfId()) != null) {
            throw new Exception("Sector Name " + shelf.getShelfName() + " is already assigned to a Sector.");
        }
        shelfRepository.save(shelf);
    }

    public List<Shelf> getAllShelfs() {
        return shelfRepository.findAll();
    }

    public Shelf getShelf(Long shelfId) {
        return shelfRepository.findByShelfId(shelfId);
    }

    @PostConstruct
    private void init() throws Exception {
        if (shelfRepository.findAll().size() < 4) {
            Shelf shelf = new Shelf();
            shelf.setShelfName("Test");
            shelf.setShelfSize("100");
            shelf.setShelfLoc("Test");
            this.saveShelf(shelf);

        }
    }
}