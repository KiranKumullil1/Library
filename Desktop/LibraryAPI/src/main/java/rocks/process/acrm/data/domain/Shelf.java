package rocks.process.acrm.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shelfId;
    @NotEmpty(message = "Please provide a name.")
    private String shelfName;
    @NotEmpty(message = "Please provide the size of the shelf (how many books can fir in a shelf)")
    private String shelfSize;
    @NotEmpty(message = "Please provide the location of the shelf")
    private String shelfLoc;
    @OneToMany(mappedBy = "shelf")
    private List<Book> books;

    public Long getShelfId() {
        return shelfId;
    }

    public void setShelfId(Long shelfId) {
        this.shelfId = shelfId;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public String getShelfSize() {
        return shelfSize;
    }

    public void setShelfSize(String shelfSize) {
        this.shelfSize = shelfSize;
    }

    public String getShelfLoc() {
        return shelfLoc;
    }

    public void setShelfLoc(String shelfLoc) {
        this.shelfLoc = shelfLoc;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> book) {
        this.books = book;
    }
}
