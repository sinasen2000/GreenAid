package app.models;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("ALL")
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long user_id;
    private long category_id;
    private double amount;
    private double xp_points;
    private Timestamp last_update;

    public Activity() {
        this.user_id = 0;
        this.category_id = 0;
        this.amount = 0;
        this.xp_points = 0;
        this.last_update = null;
    }

    public Activity(long user_id, long category_id, double amount, double xp_points, Timestamp last_update) {
        this.user_id = user_id;
        this.category_id = category_id;
        this.amount = amount;
        this.xp_points = xp_points;
        this.last_update = last_update;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public double getXp_points() {
        return xp_points;
    }

    public void setXp_points(double xp_points) {
        this.xp_points = xp_points;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
