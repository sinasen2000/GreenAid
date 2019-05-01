package app.models;

import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@SuppressWarnings({"ALL", "CheckStyle"})
@Entity
@Table(name = "user")
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 4, max = 32)
    private String username;

    @NotNull
    @Size(min = 4)
    private String password;

    private String first_name;
    private String last_name;
    private double experience_points;
    private int profile_picture;
    @SuppressWarnings("CheckStyle")
    private Timestamp last_update;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public double getExperience_points() {
        return experience_points;
    }

    @SuppressWarnings("CheckStyle")
    public void setExperience_points(double experience_points) {
        this.experience_points = experience_points;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(@SuppressWarnings("CheckStyle") Timestamp last_update) {
        this.last_update = last_update;
    }

    public int getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(int profile_picture) {
        this.profile_picture = profile_picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }
}