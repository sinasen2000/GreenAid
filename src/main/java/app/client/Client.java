package app.client;

import app.models.ActivityProjection;
import app.models.User;
import app.models.UserProjection;
import app.responses.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class Client {

    private static final String url_login = "http://localhost:8080/login";
    private static final String url_add_activity = "http://localhost:8080/user/add-activity";
    private static final String url_remove_activity = "http://localhost:8080/user/remove-activity";
    private static final String url_user_activities = "http://localhost:8080/user/activities";
    private static final String url_user_details = "http://localhost:8080/user/details";
    private static final String url_user_followings = "http://localhost:8080/user/followings";
    private static final String url_user_followed_by = "http://localhost:8080/user/followed-by";
    private static final String url_add_follow = "http://localhost:8080/user/add-following";
    private static final String url_remove_follow = "http://localhost:8080/user/remove-following";
    private static final String url_recommendation = "http://localhost:8080/user/recommendation";
    private static final String url_leaderboard = "http://localhost:8080/user/leaderboard";
    private static final String url_update_profile_picture = "http://localhost:8080/user/update-profile-picture";

    private static ObjectMapper mapper = new ObjectMapper();


    /**
     * Method that retrieves its session cookie with the login data.
     * @param username username of the user
     * @param password password of the user
     * @return returns cookie of the header
     */
    public static String getSessionCookie(String username, String password) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);

        HttpEntity<Response> response = HttpRequests.postRequest("", url_login, params);
        HttpHeaders responseHeaders = response.getHeaders();
        if (responseHeaders.getFirst(HttpHeaders.SET_COOKIE) != null) {
            return responseHeaders.getFirst(HttpHeaders.SET_COOKIE).split(";")[0];
        } else {
            return "No cookie found.";
        }

    }

    /**
     * Method used to add an activity in the database given a session, a category id
     * and the amount of times that activity has been made.
     * @param sessionCookie sessionCookie is used for this operation
     * @param categoryId categoryID is used to determine which activity wiil be added
     * @param amount amount of the activity
     * @return returns the data of the added activity
     */
    public static String addActivity(String sessionCookie, long categoryId, double amount) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("category_id", categoryId);
        params.add("amount", amount);

        HttpEntity<Response> response = HttpRequests
                .postRequest(sessionCookie, url_add_activity, params);

        return (String) response.getBody().getData();

    }

    /**
     * Method to remove activity.
     * @param sessionCookie session cookie of the user
     * @param id id of the user
     * @return returns a remove response
     */
    public static String removeActivity(String sessionCookie, long id) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("id", id);

        HttpEntity<Response> response = HttpRequests
                .postRequest(sessionCookie, url_remove_activity, params);

        return (String) response.getBody().getData();

    }

    /**
     * Lists the activities of the user.
     * @param sessionCookie uses sessionCookies to achieve this.
     * @return returns the list of the activities done by the user
     */
    public static List<ActivityProjection> getUserActivities(String sessionCookie) {

        HttpEntity<Response> response = HttpRequests.getRequest(sessionCookie, url_user_activities);
        if (response.getBody().isOk()) {
            List<ActivityProjection> activities = mapper
                    .convertValue(response.getBody()
                            .getData(), new TypeReference<List<ActivityProjection>>() {
                            });
            return activities;
        } else {
            return null;
        }
    }

    /**
     * Shows user details.
     * @param sessionCookie session  cookie of the user
     * @return returns user details.
     */
    public static User getUserDetails(String sessionCookie) {

        HttpEntity<Response> response = HttpRequests.getRequest(sessionCookie, url_user_details);
        if (response.getBody().isOk()) {
            User user = mapper.convertValue(response.getBody().getData(), User.class);
            return user;
        } else {
            return null;
        }

    }

    /**
     * Shows followings of the user.
     * @param sessionCookie cookie of the user.
     * @return returns  followings of the user.
     */
    public static List<UserProjection> getUserFollowings(String sessionCookie) {

        HttpEntity<Response> response = HttpRequests.getRequest(sessionCookie, url_user_followings);
        if (response.getBody().isOk()) {
            List<UserProjection> followings = mapper
                    .convertValue(response.getBody().getData(),
                            new TypeReference<List<UserProjection>>() {
                            });
            return followings;
        } else {
            return null;
        }
    }

    /**
     * Gets the followers of a user.
     * @param sessionCookie sessioncookie of user
     * @return returns the followers
     */
    public static List<UserProjection> getUserFollowedBy(String sessionCookie) {

        HttpEntity<Response> response = HttpRequests
                .getRequest(sessionCookie, url_user_followed_by);
        if (response.getBody().isOk()) {
            List<UserProjection> followedBy = mapper
                    .convertValue(response.getBody()
                            .getData(), new TypeReference<List<UserProjection>>() {
                            });
            return followedBy;
        } else {
            return null;
        }
    }

    /**
     * Manages the follow requests.
     * @param sessionCookie sessioncookie of user
     * @return returns the follow requests.
     */
    private static  String manageFollowRequests(String sessionCookie, String username, String url) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", username);

        HttpEntity<Response> response = HttpRequests.postRequest(sessionCookie, url, params);

        return (String) response.getBody().getData();

    }

    /**
     * Manages the follow requests.
     * @param sessionCookie sessioncookie of user
     * @return returns the follow requests.
     */
    public static String addFollow(String sessionCookie, String username) {

        return manageFollowRequests(sessionCookie, username, url_add_follow);

    }

    /**
     * Manages the follow requests.
     * @param sessionCookie sessioncookie of user
     * @return removes the follow requests.
     */
    public static String removeFollow(String sessionCookie, String username) {

        return manageFollowRequests(sessionCookie, username, url_remove_follow);

    }

    /**
     * Gives a recommendation based on the activities.
     * @param sessionCookie sessioncookie of user
     * @return recommendations
     */
    public static String getRecommendation(String sessionCookie) {
        HttpEntity<Response> response = HttpRequests.getRequest(sessionCookie, url_recommendation);
        return (String) response.getBody().getData();
    }

    /**
     * Creates a top-20 leaderboard.
     * @param sessionCookie sessioncookie of user
     * @return returns the leaderboard.
     */
    public static List<UserProjection> getLeaderboard(String sessionCookie) {
        HttpEntity<Response> response = HttpRequests.getRequest(sessionCookie, url_leaderboard);
        if (response.getBody().isOk()) {
            List<UserProjection> leaderboard = mapper
                    .convertValue(response.getBody()
                            .getData(), new TypeReference<List<UserProjection>>() {
                            });
            return leaderboard;
        } else {
            return null;
        }
    }

    /**
     * Updates the profile picture of the user.
     * @param sessionCookie cookie to be used
     * @param profile_picture profile picture of the user.
     * @return
     */
    @SuppressWarnings("CheckStyle")
    public static String updateProfilePicture(String sessionCookie,
                                              int profile_picture) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("profile_picture", profile_picture);

        HttpEntity<Response> response = HttpRequests
                .postRequest(sessionCookie, url_update_profile_picture, params);

        return (String) response.getBody().getData();

    }

}
