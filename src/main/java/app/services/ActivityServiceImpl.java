package app.services;

import app.authentication.SecurityServiceImpl;
import app.models.Activity;
import app.models.ActivityProjection;
import app.models.User;
import app.repository.ActivityRepository;
import app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ActivityServiceImpl {

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Method that adds an activity to a repository and saves it to the database.
     * @param activity activity to add
     */
    public void save(Activity activity) {

        activityRepository.save(activity);
    }

    /**
     * Deletes activity.
     * @param activity activity to be deleted
     */
    public void delete(Activity activity) {

        activityRepository.delete(activity);
    }

    /**
     * Method that finds a user by his/her activities.
     * @param userId id of the user
     * @return returns the activities of the user.
     */
    public List<Activity> findByUser_id(long userId) {

        return activityRepository.findByUser_id(userId);
    }

    /**
     * Finds an activity by ID.
     * @param id id to be found
     * @return returns the activity
     */
    public Activity findById(long id) {

        return activityRepository.findById(id);
    }

    /**
     * Returns the activities of a user.
     * @param user user of the app
     * @return
     */
    public List<ActivityProjection> getActivities(User user) {

        List<Activity> activities = activityRepository.findByUser_id(user.getId());
        List<ActivityProjection> activityArray = new LinkedList<>();

        for (Activity a : activities) {

            long id = a.getId();
            double amount = a.getAmount();
            @SuppressWarnings("CheckStyle") double xp_points = a.getXp_points();
            String category;
            switch ((int) a.getCategory_id()) {

                case 1:
                    category = "Eating a vegetarian meal";
                    break;
                case 2:
                    category = "Buying local produce";
                    break;
                case 3:
                    category = "Using bike instead of car";
                    break;
                case 4:
                    category = "Using public transport instead of car";
                    break;
                case 5:
                    category = "Installing solar panels";
                    break;
                case 6:
                    category = "Lowering the temperature of your home";
                    break;
                default:
                    category = "unknown";
            }
            activityArray.add(new ActivityProjection(id, user.getUsername(),
                    category, amount, xp_points));
        }
        return activityArray;
    }

    /**
     * Returns recommendations for a user.
     * @param user user of the app
     * @return
     */
    public String getRecommendation(User user) {
        RecommendationRepository repo = new RecommendationRepository();
        List<String> eatRecommendations = repo.getEatRecommendations();
        List<String> householdRecommendations = repo.getHouseholdRecommendations();
        List<String> transportRecommendations = repo.getTransportRecommendations();
        List<Activity> activities = activityRepository.findByUser_id(user.getId());
        int eat = 0;
        int transport = 0;
        int household = 0;
        String activityRecom = "";
        for (Activity a : activities) {

            double amount = a.getAmount();

            switch ((int) a.getCategory_id()) {
                case 1:
                    eat += amount;
                    break;
                case 2:
                    eat += amount;
                    transport += amount;
                    break;
                case 3:
                    transport += amount;
                    break;
                case 4:
                    transport += amount;
                    break;
                case 5:
                    household += amount;
                    break;
                case 6:
                    household += amount;
                    break;
                default:
                    break;
            }
        }
        int max = 0;
        if (eat > max) {
            max = eat;
        }
        if (transport > max) {
            max = transport;
        }
        if (household > max) {
            max = household;
        }
        if (eat == max) {
            int rand = (int) (Math.random() * 8);
            activityRecom += "Food\n" + eatRecommendations.get(rand);
        } else if (household == max) {
            int rand = (int) (Math.random() * 6);
            activityRecom += "Household\n" + householdRecommendations.get(rand);
        } else {
            int rand = (int) (Math.random() * 6);
            activityRecom += "Transportation\n" + transportRecommendations.get(rand);
        }

        return "Based on your activities, here's an activity recommendation:\nCategory: "
                + activityRecom;
    }

    /**
     * Removes activity of a user.
     * @param activity selected activity
     * @return
     */
    public String removeActivity(Activity activity) {
        if (activity != null) {

            if (activity.getUser_id() == userService
                    .findByUsername(securityService.findLoggedInUsername()).getId()) {

                activityRepository.delete(activity);
                return "Activity \""
                        + categoryRepository.findById(activity
                        .getCategory_id()).getName() + "\" removed successfully!";

            }
            throw new RuntimeException("You can't remove someone else's activity!");
        }
        return "Activity not found!";
    }
}
