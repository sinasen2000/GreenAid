package app.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category_type")
public class CategoryType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // suppressed for naming
    @SuppressWarnings("CheckStyle")
    private String category_name;

    public CategoryType() {
        this.category_name = "";
    }

    @SuppressWarnings("CheckStyle")
    public CategoryType(String category_name) {
        this.category_name = category_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    @SuppressWarnings("CheckStyle")
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
