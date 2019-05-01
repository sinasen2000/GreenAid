package app.models;



@SuppressWarnings("ALL")
public class ActivityProjection {

    long id;
    String username;
    String category;
    double amount;
    double xp_points;


    /**
     * Class that projects user activities.
     */
    public ActivityProjection() {
        this.id = 0;
        this.username = new String();
        this.category = new String();
        this.amount = 0.0;
        this.xp_points = 0.0;
    }

    /**
     * Constructor of the class that  projects the activities in the homepage of the app.
     *
     * @param username username of the user
     * @param category category of the activity
     * @param amount amount of the activity
     * @param xp_points xp points of that activity given the amount
     */
    public ActivityProjection(long id, String username, String category,
                              double amount, double xp_points) {
        this.id = id;
        this.username = username;
        this.category = category;
        this.amount = amount;
        this.xp_points = xp_points;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getXp_points() {
        return xp_points;
    }

    public void setXp_points(double xp_points) {
        this.xp_points = xp_points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActivityProjection that = (ActivityProjection) o;
        return id == that.id;
    }

}
