package app.services;

import app.models.Following;
import app.repository.FollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowingServiceImpl {

    @Autowired
    FollowingRepository followingRepository;

    /**
     * Save the following method.
     * @param following  model to be used.
     */
    public void save(Following following) {

        followingRepository.save(following);

    }

    /**
     * Delete the following.
     * @param following model to be used.
     */
    public void delete(Following following) {

        followingRepository.delete(following);

    }

    /**
     * Finds user by id.
     * @param user_id_1 id of user
     * @param user_id_2 id of searched user
     * @return
     */
    public Following findById1Id2(@SuppressWarnings("CheckStyle")
                                          long user_id_1, @SuppressWarnings("CheckStyle") long user_id_2) {

        return followingRepository.findById1Id2(user_id_1, user_id_2);

    }

}
