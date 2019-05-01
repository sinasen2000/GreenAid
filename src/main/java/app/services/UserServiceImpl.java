package app.services;

import app.authentication.SecurityServiceImpl;
import app.models.Following;
import app.models.User;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


@SuppressWarnings("ALL")
@Service
public class UserServiceImpl {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private FollowingServiceImpl followingService;

    @Autowired
    private UserRepository userRepository;


    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null)
            userRepository.delete(user);
        else
            throw new UsernameNotFoundException("Username not found");
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * gets the user.
     * @return returns the username
     */
    public User getLoggedInUser() {
        String loggedInUserUsername = securityService.findLoggedInUsername();

        if (loggedInUserUsername == null) {
            return null;
        }

        return userRepository.findByUsername(loggedInUserUsername);
    }

    /**
     * Finds followings.
     * @return returns the followed users
     */
    public List<User> findFollowings(long userId) {

        return userRepository.findFollowings(userId);

    }

    /**
     * Finds follower.
     * @return returns the followers
     */
    public List<User> findFollowedBy(long userId) {

        return userRepository.findFollowedBy(userId);

    }

    public List<User> findLeaderboard() {
        return userRepository.findLeaderboard();
    }

    public String addFollowing(Following following, String username) {
        if (userRepository.findByUsername(username) != null) {

            if (securityService.findLoggedInUsername().equals(username)) {
               throw new RuntimeException("You already follow yourself...");
            }

            User followedUser = userRepository.findByUsername(username);
            if (followingService.findById1Id2(getLoggedInUser().getId(), followedUser.getId()) == null) {

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                following.setUser_id_1(
                        userRepository.findByUsername(securityService.findLoggedInUsername()).getId());
                following.setUser_id_2(followedUser.getId());
                following.setLast_update(timestamp);
                followingService.save(following);
                return  "Your followings have been updated!";
            }

            throw new RuntimeException("You already follow this user!");

        } else {
            throw new RuntimeException("User not found.");
        }
    }

    public String removeFollowing(String username) {
        if (userRepository.findByUsername(username) != null) {

            if (securityService.findLoggedInUsername().equals(username)) {

                throw new RuntimeException("You cannot unfollow yourself...");

            }

            followingService.delete(followingService
                    .findById1Id2(userRepository.findByUsername(securityService.findLoggedInUsername()).getId(),
                            userRepository.findByUsername(username).getId()));
            return "Your followings have been updated!";

        }
        throw new RuntimeException("User not found.");
    }

    public void updateProfilePicture(int profile_picture, long id) {

        userRepository.updateProfilePicture(profile_picture, id);

    }

}

