package app.repository;

import app.models.Following;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FollowingRepository extends JpaRepository<Following, Long> {

    @Query(value = "SELECT * FROM following WHERE (user_id_1 = :id_1) and "
            + "(user_id_2 = :id_2)", nativeQuery = true)
    Following findById1Id2(@SuppressWarnings("CheckStyle")
                                   long id_1, @SuppressWarnings("CheckStyle") long id_2);

}
