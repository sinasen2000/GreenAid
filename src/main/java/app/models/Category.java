package app.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("ALL")
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private long amount_saved;
    private double xp_points;
    private long category_type_id;

    public Category() {
        this.name = "";
        this.amount_saved = 0L;
        this.xp_points = 0.0;
        this.category_type_id = 0L;
    }

    public Category(String name, long amount_saved, double xp_points, long category_type_id) {
        this.name = name;
        this.amount_saved = amount_saved;
        this.xp_points = xp_points;
        this.category_type_id = category_type_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount_saved() {
        return amount_saved;
    }

    public void setAmount_saved(long amount_saved) {
        this.amount_saved = amount_saved;
    }

    public double getXp_points() {
        return xp_points;
    }

    public void setXp_points(double xp_points) {
        this.xp_points = xp_points;
    }

    public long getCategory_type_id() {
        return category_type_id;
    }

    public void setCategory_type_id(long category_type_id) {
        this.category_type_id = category_type_id;
    }
}
