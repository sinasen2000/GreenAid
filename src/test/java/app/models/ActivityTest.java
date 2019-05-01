package app.models;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ActivityTest {

    private final static double DELTA = 0;
    private Activity activity;

    @Before
    public void setUp() {
        activity = new Activity(1, 1, 1.0, 1.0, new Timestamp(System.currentTimeMillis()));
    }

    @After
    public void tearDown() {
        activity = null;
    }

    @Test
    public void constructorDefault() {
        activity = new Activity();
        assertNotNull(activity);
    }

    @Test
    public void constructorParameters() {
        activity = new Activity(1, 1, 1.0, 1.0, new Timestamp(System.currentTimeMillis()));
        assertNotNull(activity);
    }

    @Test
    public void getUser_id() {
        assertEquals(1, activity.getUser_id());
    }

    @Test
    public void setUser_id() {
        activity.setUser_id(2);
        assertEquals(2, activity.getUser_id());
    }

    @Test
    public void getCategory_id() {
        assertEquals(1, activity.getCategory_id());
    }

    @Test
    public void setCategory_id() {
        activity.setCategory_id(2);
        assertEquals(2, activity.getCategory_id());
    }

    @Test
    public void getLast_update() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        activity = new Activity(1, 1, 1.0, 1.0, timestamp);
        assertEquals(timestamp, activity.getLast_update());
    }

    @Test
    public void setLast_update() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        activity.setLast_update(timestamp);
        assertEquals(timestamp, activity.getLast_update());
    }

    @Test
    public void getXp_points() {
        assertEquals(1.0, activity.getXp_points(), DELTA);
    }

    @Test
    public void setXp_points() {
        activity.setXp_points(2.0);
        assertEquals(2.0, activity.getXp_points(), DELTA);
    }

    @Test
    public void getId() {
        assertEquals(0, activity.getId());
    }

    @Test
    public void setId() {
        activity.setId(2);
        assertEquals(2, activity.getId());
    }

    @Test
    public void getAmount() {
        assertEquals(1.0, activity.getAmount(), DELTA);
    }

    @Test
    public void setAmount() {
        activity.setAmount(2.0);
        assertEquals(2.0, activity.getAmount(), DELTA);
    }
}