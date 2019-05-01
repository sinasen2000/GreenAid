package app.repository;

import app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value = "SELECT * FROM user where id in (select user_id_2 "
            + "from following where user_id_1 = :id)", nativeQuery = true)
    List<User> findFollowings(long id);

    @Query(value = "SELECT * FROM user where id in (select user_id_1 from following "
            + "where user_id_2 = :id)", nativeQuery = true)
    List<User> findFollowedBy(long id);

    @Query(value = "SELECT * FROM user ORDER BY "
            + "experience_points DESC LIMIT 20", nativeQuery = true)
    List<User> findLeaderboard();

    @SuppressWarnings("CheckStyle")
    @Modifying
    @Transactional
    @Query(value = "UPDATE user u SET u.profile_picture = :profile_picture "
            + "WHERE u.id = :id", nativeQuery = true)
    void updateProfilePicture(int profile_picture, long id);

}