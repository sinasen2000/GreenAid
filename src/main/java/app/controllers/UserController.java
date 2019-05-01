package app.controllers;

import app.authentication.SecurityServiceImpl;
import app.models.Activity;
import app.models.ActivityProjection;
import app.models.Following;
import app.models.User;
import app.models.UserProjection;
import app.repository.CategoryRepository;
import app.responses.Response;
import app.services.ActivityServiceImpl;
import app.services.FollowingServiceImpl;
import app.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@RestController()
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private ActivityServiceImpl activityService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FollowingServiceImpl followingService;

    /**
     * Returns JSON into string.
     *
     * @param input input of the projection
     * @param id1 id of the user
     * @return
     */
    public List<UserProjection> toUserProjection(List<User> input, long id1) {

        List<UserProjection> output = new LinkedList<>();
        for (User u : input) {
            boolean following = followingService.findById1Id2(id1, u.getId()) != null;
            output.add(new UserProjection(u.getUsername(), u.getFirst_name(),
                    u.getLast_name(), u.getExperience_points(), u.getLast_update(), following));

        }

        return output;

    }

    /**
     * Maps it to /details.
     *
     * @return
     */
    @GetMapping("/details")
    public Response getUserDetails() {
        if (securityService.findLoggedInUsername() != null) {
            return new Response(true,
                    userService.findByUsername(securityService.findLoggedInUsername()));
        } else {
            return new Response(false, "User not logged in.");
        }
    }

    /**
     * Maps it to /info.
     *
     * @return
     */
    @PostMapping("/info")
    public Response getUserInfo(String username) {
        String loggedInUsername = securityService.findLoggedInUsername();
        if (loggedInUsername != null) {
            User user = userService.findByUsername(username);
            User loggedInUser = userService.findByUsername(loggedInUsername);
            boolean following = followingService.findById1Id2(loggedInUser.getId(),
                    user.getId()) != null;
            return new Response(true, new UserProjection(user.getUsername(),
                    user.getFirst_name(), user.getLast_name(),
                    user.getExperience_points(), user.getLast_update(), following));
        } else {
            return new Response(false, "User not logged in.");
        }
    }

    /**
     * Get request to /showactivities to show recent activities of a user.
     *
     * @return returns recent activities of a user
     */
    @GetMapping("/activities")
    public Response showActivities() {

        String username = securityService.findLoggedInUsername();
        if (username != null) {
            User user = userService.findByUsername(username);
            List<ActivityProjection> response = activityService.getActivities(user);

            return new Response(true, response);
        } else {
            return new Response(false, "You are not authorized!");
        }
    }

    /**
     * Maps to /activity with a POST request.
     *
     * @param activity adds and activity to user profile.
     * @return returns the updated version of activities.
     * @throws Exception throws exception in case things go wrong.
     */
    @PostMapping("/add-activity")
    public Response addActivity(Activity activity) {

        String username = securityService.findLoggedInUsername();
        if (username != null) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            activity.setUser_id(userService.findByUsername(username).getId());
            activity.setXp_points(categoryRepository.findById(activity
                    .getCategory_id()).getXp_points() * activity.getAmount());
            activity.setLast_update(timestamp);
            activityService.save(activity);

            return new Response(true, "Activity \""
                    + categoryRepository.findById(activity.getCategory_id()).getName()
                    + "\" saved successfully!");
        } else {
            return new Response(false, "You are not authorized!");
        }
    }

    /**
     * Mapping of remove activity.
     * @param id id of the user
     * @return
     */
    @SuppressWarnings("CheckStyle")
    @PostMapping("/remove-activity")
    public Response removeActivity(long id) {

        if (securityService.findLoggedInUsername() != null) {
            Activity activity = activityService.findById(id);
            try {
                String succesMessage = activityService.removeActivity(activity);
                return new Response(true, succesMessage);
            } catch (Exception e) {
                return new Response(false, e.getMessage());
            }
        } else {
            return new Response(false, "You are not authorized!");
        }
    }


    /**
     * Gets the followings.
     *
     * @return
     */
    @GetMapping("/followings")
    public Response getFollowings() {
        String loggedInUsername = securityService.findLoggedInUsername();
        if (loggedInUsername != null) {
            User foundUser = userService.findByUsername(loggedInUsername);
            List<User> query = userService.findFollowings(foundUser.getId());
            return new Response(true, toUserProjection(query, foundUser.getId()));

        } else {
            return new Response(false, "You are not authorized!");
        }
    }

    /**
     * gets the followers.
     *
     * @return
     */
    @GetMapping("/followed-by")
    public Response getFollowedBy() {
        String loggedInUsername = securityService.findLoggedInUsername();
        if (loggedInUsername != null) {
            User foundUser = userService.findByUsername(loggedInUsername);
            List<User> query = userService.findFollowedBy(foundUser.getId());
            return new Response(true, toUserProjection(query, foundUser.getId()));

        } else {
            return new Response(false, "You are not authorized!");
        }
    }

    /**
     * Adds a following.
     *
     * @param following model to be used.
     * @param username username of the following person
     * @return returns response
     */
    @SuppressWarnings("CheckStyle")
    @PostMapping("/add-following")
    public Response addFollowing(Following following, String username) {

        if (securityService.findLoggedInUsername() != null) {

            try {
                String succesMessage = userService.addFollowing(following, username);
                return new Response(true, succesMessage);
            } catch (Exception e) {
                return new Response(false, e.getMessage());
            }
        } else {
            return new Response(false, "You are not authorized!");
        }
    }

    /**
     * Removes the following.
     * @param username username of the user.
     * @return
     */
    @SuppressWarnings("CheckStyle")
    @PostMapping("/remove-following")
    public Response removeFollowing(String username) {

        if (securityService.findLoggedInUsername() != null) {

            try {
                String succesMessage = userService.removeFollowing(username);
                return new Response(true, succesMessage);
            } catch (Exception e) {
                return new Response(false, e.getMessage());
            }
        } else {
            return new Response(false, "You are not authorized!");
        }
    }

    /**
     * gets recommendations.
     *
     * @return
     */
    @GetMapping("/recommendation")
    public Response getRecommendation() {
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        if (user != null) {
            String result = activityService.getRecommendation(user);
            return new Response(true, result);

        } else {
            return new Response(false, "You are not authorized!");
        }
    }

    /**
     * Gets leaderboard.
     *
     * @return
     */
    @GetMapping("/leaderboard")
    public Response getLeaderboard() {

        if (securityService.findLoggedInUsername() != null) {
            List<User> query = userService.findLeaderboard();
            return new Response(true, toUserProjection(query,
                    userService.findByUsername(securityService.findLoggedInUsername()).getId()));
        } else {
            return new Response(false, "You are not authorized!");
        }
    }

    /**
     * Updates the profile picture of the user.
     * @param profile_picture profile picture of the user.
     * @return
     */
    @PostMapping("/update-profile-picture")
    public Response updateUserPicture(@SuppressWarnings("CheckStyle") int profile_picture) {

        if (securityService.findLoggedInUsername() != null) {
            User user = userService.findByUsername(securityService.findLoggedInUsername());
            userService.updateProfilePicture(profile_picture, user.getId());
            return new Response(true, "Your profile picture has been updated!");
        } else {
            return new Response(false, "You are not authorized!");
        }

    }

}
