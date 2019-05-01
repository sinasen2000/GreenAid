package app.controllers;

import app.authentication.SecurityServiceImpl;
import app.models.*;
import app.repository.CategoryRepository;
import app.responses.Response;
import app.services.ActivityServiceImpl;
import app.services.FollowingServiceImpl;
import app.services.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest//(webEnvironment  = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private ActivityServiceImpl activityService;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private SecurityServiceImpl securityService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private FollowingServiceImpl followingService;

    @Test
    public void toUserProjectionSuccess() {
        List<UserProjection> expected = new ArrayList<>();

        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(2);
        user.setUsername("username-test2");
        user.setFirst_name("first-name-test2");
        user.setLast_name("last-name-test2");
        user.setExperience_points(1.0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setLast_update(timestamp);

        users.add(user);

        expected.add(new UserProjection("username-test2", "first-name-test2", "last-name-test2", 1.0, timestamp, true));

        Mockito.when(followingService.findById1Id2(1, 2))
                .thenReturn(new Following());

        List<UserProjection> result = userController.toUserProjection(users, 1);

        Mockito.verify(followingService).findById1Id2(1, 2);

        assertEquals(expected, result);
    }

    @Test
    public void getUserDetailsNotAuthorized() {
        Response expected = new Response(false, "User not logged in.");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.getUserDetails();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void getUserDetailsSuccess() {
        Response expected;

        User user = new User();
        user.setId(1);
        user.setUsername("username-test");

        expected = new Response(true, user);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);

        Response result = userController.getUserDetails();

        Mockito.verify(securityService, times(2)).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");

        assertEquals(expected, result);
    }

    @Test
    public void getUserInfoNotAuthorized() {
        Response expected = new Response(false, "User not logged in.");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.getUserInfo("username-test");

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void getUserInfoSuccessFollowingFalse() {
        Response expected;

        User user = new User();
        user.setId(1);

        UserProjection expectedProjection =  new UserProjection(user.getUsername(), user.getFirst_name(), user.getLast_name(), user.getExperience_points(), user.getLast_update(), false);

        expected = new Response(true, expectedProjection);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);
        Mockito.when(followingService.findById1Id2(1, 1)).thenReturn(null);

        Response result = userController.getUserInfo("username-test");

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService, times(2)).findByUsername(any(String.class));

        assertEquals(expected, result);
    }

    @Test
    public void getUserInfoSuccessFollowingTrue() {
        Response expected;

        User user = new User();
        user.setId(1);

        UserProjection expectedProjection =  new UserProjection(user.getUsername(), user.getFirst_name(), user.getLast_name(), user.getExperience_points(), user.getLast_update(), true);

        expected = new Response(true, expectedProjection);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);
        Mockito.when(followingService.findById1Id2(1, 1)).thenReturn(new Following());

        Response result = userController.getUserInfo("username-test");

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService, times(2)).findByUsername(any(String.class));

        assertEquals(expected, result);
    }

    @Test
    public void showActivitiesNotAuthorized() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.showActivities();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void showActivitiesSucces() {
        User user = new User();
        user.setUsername("Name");

        List<ActivityProjection> activityArray = new LinkedList<>();

        Response expected = new Response(true, activityArray);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("Name");
        Mockito.when(activityService.getActivities(user)).thenReturn(activityArray);

        Response result = userController.showActivities();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void addActivityNotAuthorized() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.addActivity(new Activity());

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void addActivitySuccess() {
        Response expected = new Response(true, "Activity \"category-test\" saved successfully!");

        Activity activity = new Activity();
        activity.setCategory_id(1);
        activity.setAmount(1.0);

        User user = new User();
        user.setId(1L);

        Category category = new Category();
        category.setXp_points(1.0);
        category.setName("category-test");

        Mockito.doAnswer((i) -> null).when(activityService).save(any(Activity.class));
        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);
        Mockito.when(categoryRepository.findById(1))
                .thenReturn(category);

        Response result = userController.addActivity(activity);

        Mockito.verify(activityService).save(any(Activity.class));
        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername(any(String.class));
        Mockito.verify(categoryRepository, times(2)).findById(1);

        assertEquals(expected, result);
    }

    @Test
    public void removeActivityNotAuthorized() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.removeActivity(1);

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void removeActivityExeption() {
        Response expected = new Response(false, "Exception scenario");

        Activity activity = new Activity();
        activity.setId(1);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(activityService.findById(1)).thenReturn(activity);
        Mockito.when(activityService.removeActivity(activity)).thenThrow(new RuntimeException("Exception scenario"));

        Response result = userController.removeActivity(1);

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(activityService).findById(1);
        Mockito.verify(activityService).removeActivity(activity);

        assertEquals(expected, result);
    }

    @Test
    public void removeActivitySucces() {
        Response expected = new Response(true, "succes");

        Activity activity = new Activity();

        Mockito.when(securityService.findLoggedInUsername()).thenReturn("username-test");
        Mockito.when(activityService.findById(1)).thenReturn(activity);
        Mockito.when(activityService.removeActivity(activity)).thenReturn("succes");

        Response result = userController.removeActivity(1);

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(activityService).findById(1);
        Mockito.verify(activityService).removeActivity(activity);

        assertEquals(expected, result);
    }

    @Test
    public void getFollowingsNotAuthorized() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.getFollowings();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void getUserFollowingsSuccess() {
        Response expected;

        User user = new User();
        user.setId(1);

        List<User> userList = new ArrayList<>();
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("user-following-test");
        user2.setLast_name("last-name-test");
        user2.setFirst_name("first-name-test");
        user2.setExperience_points(1.0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user2.setLast_update(timestamp);
        userList.add(user2);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(userService.findFollowings(1))
                .thenReturn(userList);
        Mockito.when(followingService.findById1Id2(1, 2))
                .thenReturn(new Following());

        expected = new Response(true, userController.toUserProjection(userList, user.getId()));

        Response result = userController.getFollowings();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(userService).findFollowings(1);
        Mockito.verify(followingService, times(2)).findById1Id2(1, 2);

        assertEquals(expected, result);
    }

    @Test
    public void getFollowedByNotAuthorized() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.getFollowedBy();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void getFollowedBySuccess() {
        Response expected;

        User user = new User();
        user.setId(1);

        List<User> userList = new ArrayList<>();
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("user-following-test");
        user2.setLast_name("last-name-test");
        user2.setFirst_name("first-name-test");
        user2.setExperience_points(1.0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user2.setLast_update(timestamp);
        userList.add(user2);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(userService.findFollowedBy(1))
                .thenReturn(userList);
        Mockito.when(followingService.findById1Id2(1, 2))
                .thenReturn(null);

        expected = new Response(true, userController.toUserProjection(userList, user.getId()));

        Response result = userController.getFollowedBy();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(userService).findFollowedBy(1);
        Mockito.verify(followingService, times(2)).findById1Id2(1, 2);

        assertEquals(expected, result);
    }

    @Test
    public void addFollowingNotAuthorized() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.addFollowing(new Following(), "username-test");

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void addFollowingException() {
        Response expected = new Response(false, "Exception scenario");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.addFollowing(new Following(), "username-test"))
                .thenThrow(new RuntimeException("Exception scenario"));

        Response result = userController.addFollowing(new Following(), "username-test");

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void addFollowingSucces() {
        Response expected = new Response(true, "Yes, succes");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.addFollowing(new Following(), "username-test"))
                .thenReturn("Yes, succes");

        Response result = userController.addFollowing(new Following(), "username-test");

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void removeFollowingNotAuthorized() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.removeFollowing("username-test");

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void removeFollowingExeption() {
        Response expected = new Response(false, "exceptionTest");

        Mockito.when(securityService.findLoggedInUsername()).thenReturn("username-test");
        Mockito.when(userService.removeFollowing("username-test")).thenThrow(new RuntimeException("exceptionTest"));

        Response result = userController.removeFollowing("username-test");

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).removeFollowing("username-test");

        assertEquals(expected, result);
    }

    @Test
    public void removeFollowingSucces() {
        Response expected = new Response(true, "succes");

        Mockito.when(securityService.findLoggedInUsername()).thenReturn("username-test");
        Mockito.when(userService.removeFollowing("username-test")).thenReturn("succes");

        Response result = userController.removeFollowing("username-test");

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).removeFollowing("username-test");

        assertEquals(expected, result);
    }

    @Test
    public void getRecommendationNotAuthorized() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(null);

        Response result = userController.getRecommendation();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");

        assertEquals(expected, result);
    }

    @Test
    public void getRecommendationSucces() {
        Response expected = new Response(true, "succes");

        User user = new User();

        Mockito.when(securityService.findLoggedInUsername()).thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test")).thenReturn(user);
        Mockito.when(activityService.getRecommendation(user)).thenReturn("succes");

        Response result = userController.getRecommendation();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(activityService).getRecommendation(user);

        assertEquals(expected, result);
    }

    @Test
    public void getLeaderboardNotAuthorized() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.getLeaderboard();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void getLeadeboardSuccess() {
        Response expected;

        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        User user1 = new User();
        user1.setId(2);
        user1.setUsername("username-test2");
        user1.setFirst_name("first-name-test2");
        user1.setLast_name("last-name-test2");
        user1.setExperience_points(1.0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user1.setLast_update(timestamp);

        users.add(user1);


        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findLeaderboard())
                .thenReturn(users);
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(followingService.findById1Id2(1, 2))
                .thenReturn(new Following());

        expected = new Response(true, userController.toUserProjection(users, 1));

        Response result = userController.getLeaderboard();

        Mockito.verify(securityService, times(2)).findLoggedInUsername();
        Mockito.verify(userService).findLeaderboard();
        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(followingService, times(2)).findById1Id2(1, 2);

        assertEquals(expected, result);
    }

    @Test
    public void updateUserPictureFail() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.updateUserPicture(1);

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void updateUserPictureSuccess() {
        Response expected = new Response(true, "Your profile picture has been updated!");

        User user = new User();
        user.setId(2);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.doAnswer((i) -> null).when(userService).updateProfilePicture(1, 2);

        Response result = userController.updateUserPicture(1);

        Mockito.verify(securityService, times(2)).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(userService).updateProfilePicture(1, 2);

        assertEquals(expected, result);
    }
}