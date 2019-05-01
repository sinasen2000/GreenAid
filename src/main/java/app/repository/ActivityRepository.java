package app.repository;

import app.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query(value = "SELECT * FROM activity WHERE user_id = :id", nativeQuery = true)
    List<Activity> findByUser_id(long id);

    Activity findById(long id);

}
