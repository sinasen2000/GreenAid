package app.services;

import app.authentication.SecurityServiceImpl;
import app.models.Activity;
import app.models.ActivityProjection;
import app.models.Category;
import app.models.User;
import app.repository.ActivityRepository;
import app.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityServiceImplTest {

    @Autowired
    private ActivityServiceImpl activityService;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @MockBean
    private SecurityServiceImpl securityServiceMock;

    @MockBean
    private UserServiceImpl userServiceMock;

    @MockBean
    private ActivityRepository activityRepositoryMock;

    @MockBean
    private CategoryRepository categoryRepositoryMock;

    Activity activity_1;

    @Before
    public void setUp() {
        activity_1 = new Activity();
        activity_1.setId(1);
        activity_1.setUser_id(3);
    }

    @Test
    public void saveActivity() {
        Mockito.when(activityRepositoryMock.save(activity_1)).thenReturn(activity_1);

        this.activityService.save(activity_1);

        Mockito.verify(activityRepositoryMock).save(activity_1);
    }

    @Test
    public void deleteActivity() {
        Mockito.doAnswer((i) -> {
            Assert.assertEquals(activity_1, i.getArgument(0));
            return null;
        }).when(activityRepositoryMock).delete(activity_1);

        this.activityService.delete(activity_1);

        Mockito.verify(activityRepositoryMock).delete(activity_1);
    }

    @Test
    public void findActivityByUserId() {
        Activity activity_2 = new Activity();
        activity_2.setId(2);
        activity_2.setUser_id(3);

        List activityList = new ArrayList();
        activityList.add(activity_1);
        activityList.add(activity_2);

        Mockito.when(activityRepositoryMock.findByUser_id(3)).thenReturn(activityList);

        List result = this.activityService.findByUser_id(3);

        Mockito.verify(activityRepositoryMock).findByUser_id(3);

        Assert.assertEquals(activityList, result);
    }

    @Test
    public void findActivityById() {
        Mockito.when(activityRepositoryMock.findById(1)).thenReturn(activity_1);

        Activity result = activityService.findById(1);

        Mockito.verify(activityRepositoryMock).findById(1);

        Assert.assertEquals(activity_1, result);
    }

    @Test
    public void showActivitiesSuccessUnknownCase() {

        testShowActivityCase(0, "unknown");
    }

    @Test
    public void showActivitiesSuccessMealCase() {

        testShowActivityCase(1, "Eating a vegetarian meal");
    }

    @Test
    public void showActivitiesSuccessLocalProduceCase() {

        testShowActivityCase(2, "Buying local produce");
    }

    @Test
    public void showActivitiesSuccessBikeCase() {

        testShowActivityCase(3, "Using bike instead of car");
    }

    @Test
    public void showActivitiesSuccessPublicTransportCase() {

        testShowActivityCase(4, "Using public transport instead of car");
    }

    @Test
    public void showActivitiesSuccessSolarPanelsCase() {

        testShowActivityCase(5, "Installing solar panels");
    }

    @Test
    public void showActivitiesSuccessHomeTemperatureCase() {

        testShowActivityCase(6, "Lowering the temperature of your home");
    }

    private void testShowActivityCase(int categoryId, String expectedActivity) {
        List<ActivityProjection> expected = new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setUsername("username-test");

        List<Activity> activities = new ArrayList<>();
        Activity activity = new Activity();
        activity.setId(1);
        activity.setAmount(1.0);
        activity.setXp_points(1.0);

        activity.setCategory_id(categoryId);
        activities.add(activity);


        expected.add(new ActivityProjection(1,"username-test", expectedActivity, 1.0, 1.0));

        Mockito.when(activityRepositoryMock.findByUser_id(1))
                .thenReturn(activities);

        List<ActivityProjection> result = activityService.getActivities(user);

        Mockito.verify(activityRepositoryMock).findByUser_id(1);

        Assert.assertEquals(expected.get(0).getCategory(), result.get(0).getCategory());

    }

    @Test
    public void getRecommendationSuccessFood() {
        String expectedToContain = "Category: Food";

        User user = new User();
        user.setId(1);

        List<Activity> activities = new ArrayList<>();
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        activity1.setCategory_id(1);
        activity2.setCategory_id(2);
        activity1.setAmount(1.0);
        activity2.setAmount(1.0);

        activities.add(activity1);
        activities.add(activity2);

        Mockito.when(activityRepositoryMock.findByUser_id(1))
                .thenReturn(activities);

        String result = activityService.getRecommendation(user);

        Mockito.verify(activityRepositoryMock).findByUser_id(1);

        assertTrue(result.contains(expectedToContain));
    }

    @Test
    public void getRecommendationSuccessHousehold() {
        String expectedToContain = "Category: Household";

        User user = new User();
        user.setId(1);

        List<Activity> activities = new ArrayList<>();
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        Activity activity3 = new Activity();
        activity1.setCategory_id(5);
        activity2.setCategory_id(6);
        activity3.setCategory_id(4);
        activity1.setAmount(1.0);
        activity2.setAmount(1.0);
        activity3.setAmount(1.0);

        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);

        Mockito.when(activityRepositoryMock.findByUser_id(1))
                .thenReturn(activities);

        String result = activityService.getRecommendation(user);

        Mockito.verify(activityRepositoryMock).findByUser_id(1);

        assertTrue(result.contains(expectedToContain));
    }

    @Test
    public void getRecommendationSuccessTransportation() {
        String expectedToContain = "Category: Transportation";

        User user = new User();
        user.setId(1);

        List<Activity> activities = new ArrayList<>();
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        Activity activity3 = new Activity();
        activity1.setCategory_id(3);
        activity2.setCategory_id(4);
        activity3.setCategory_id(7);
        activity1.setAmount(1.0);
        activity2.setAmount(1.0);
        activity3.setAmount(1.0);

        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);

        Mockito.when(activityRepositoryMock.findByUser_id(1))
                .thenReturn(activities);

        String result = activityService.getRecommendation(user);

        Mockito.verify(activityRepositoryMock).findByUser_id(1);

        assertTrue(result.contains(expectedToContain));
    }

    @Test
    public void removeActivityFail() {
        String expected = "Activity not found!";

        String result = activityService.removeActivity(null);

        assertEquals(expected, result);
    }

    @Test
    public void removeActivityExeption() {
        String expected = "You can't remove someone else's activity!";

        Activity activity = new Activity();
        activity.setUser_id(1);

        User user = new User();
        user.setId(2);

        Mockito.when(securityServiceMock.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userServiceMock.findByUsername("username-test")).thenReturn(user);
        Mockito.when(activityService.findById(1))
                .thenReturn(null);

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(expected);
        activityService.removeActivity(activity);
    }

    @Test
    public void removeActivitySucces() {
        String expected = "Activity \"testCategory\" removed successfully!";

        Activity activity = new Activity();
        activity.setUser_id(1);
        activity.setCategory_id(1);

        User user = new User();
        user.setId(1);

        Category category = new Category();
        category.setName("testCategory");

        Mockito.when(securityServiceMock.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userServiceMock.findByUsername("username-test")).thenReturn(user);
        Mockito.when(categoryRepositoryMock.findById(1)).thenReturn(category);

        String result = activityService.removeActivity(activity);

        Mockito.verify(securityServiceMock).findLoggedInUsername();
        Mockito.verify(userServiceMock).findByUsername("username-test");
        Mockito.verify(categoryRepositoryMock).findById(1);

        assertEquals(expected, result);
    }
}
