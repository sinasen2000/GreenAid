package app.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.Timestamp;

import static org.junit.Assert.*;

public class UserProjectionTest {

    private static final double DELTA = 0;
    private UserProjection userProjection;

    @Before
    public void setUp() {
        userProjection = new UserProjection("username-test", "first-name-test", "last-name-test", 1.0, new Timestamp(System.currentTimeMillis()), true);
    }

    @After
    public void tearDown() {
        userProjection = null;
    }

    @Test
    public void constructorDefault() {
        userProjection = new UserProjection();
        assertNotNull(userProjection);
    }

    @Test
    public void constructorParameters() {
        userProjection = new UserProjection("username-test", "first-name-test", "last-name-test", 1.0, new Timestamp(System.currentTimeMillis()), true);
        assertNotNull(userProjection);
    }

    @Test
    public void getUsername() {
        assertEquals("username-test", userProjection.getUsername());
    }

    @Test
    public void setUsername() {
        userProjection.setUsername("username-test-2");
        assertEquals("username-test-2", userProjection.getUsername());
    }

    @Test
    public void getFirst_name() {
        assertEquals("first-name-test", userProjection.getFirst_name());
    }

    @Test
    public void setFirst_name() {
        userProjection.setFirst_name("first-name-test-2");
        assertEquals("first-name-test-2", userProjection.getFirst_name());
    }

    @Test
    public void getLast_name() {
        assertEquals("last-name-test", userProjection.getLast_name());
    }

    @Test
    public void setLast_name() {
        userProjection.setLast_name("last-name-test-2");
        assertEquals("last-name-test-2", userProjection.getLast_name());
    }

    @Test
    public void getExperience_points() {
        assertEquals(1.0, userProjection.getExperience_points(), DELTA);
    }

    @Test
    public void setExperience_points() {
        userProjection.setExperience_points(2.0);
        assertEquals(2.0, userProjection.getExperience_points(), DELTA);
    }

    @Test
    public void getLast_update() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        userProjection = new UserProjection("username-test", "first-name-test", "last-name-test", 1.0, timestamp, true);
        assertEquals(timestamp, userProjection.getLast_update());
    }

    @Test
    public void setLast_update() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        userProjection.setLast_update(timestamp);
        assertEquals(timestamp, userProjection.getLast_update());
    }

    @Test
    public void isFollowing() {
        assertTrue(userProjection.isFollowing());
    }

    @Test
    public void setFollowing() {
        userProjection.setFollowing(false);
        assertFalse(userProjection.isFollowing());
    }

    @Test
    public void equalsSameObject() {
        assertEquals(userProjection, userProjection);
    }

    @Test
    public void equalsNullObject() {
        assertNotEquals(userProjection, null);
    }

    @Test
    public void equalsDifferentClass() {
        assertNotEquals(userProjection, new String());
    }

    @Test
    public void equalsDifferentXP() {
        UserProjection userProjection2 = new UserProjection("username-test", "first-name-test", "last-name-test", 2.0, new Timestamp(System.currentTimeMillis()), true);
        assertNotEquals(userProjection, userProjection2);
    }

    @Test
    public void equalsDifferentFollowing() {
        UserProjection userProjection2 = new UserProjection("username-test", "first-name-test", "last-name-test", 1.0, new Timestamp(System.currentTimeMillis()), false);
        assertNotEquals(userProjection, userProjection2);
    }

    @Test
    public void equalsDifferentUsername() {
        UserProjection userProjection2 = new UserProjection("username-test-2", "first-name-test", "last-name-test", 1.0, new Timestamp(System.currentTimeMillis()), true);
        assertNotEquals(userProjection, userProjection2);
    }

    @Test
    public void equalsDifferentFirstName() {
        UserProjection userProjection2 = new UserProjection("username-test", "first-name-test-2", "last-name-test", 1.0, new Timestamp(System.currentTimeMillis()), true);
        assertNotEquals(userProjection, userProjection2);
    }

    @Test
    public void equalsDifferentLastName() {
        UserProjection userProjection2 = new UserProjection("username-test", "first-name-test", "last-name-test-2", 1.0, new Timestamp(System.currentTimeMillis()), true);
        assertNotEquals(userProjection, userProjection2);
    }

    @Test
    public void equalsDifferentLastUpdate() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()+1);
        UserProjection userProjection2 = new UserProjection("username-test", "first-name-test", "last-name-test", 1.0, timestamp, true);
        assertNotEquals(userProjection, userProjection2);
    }

    @Test
    public void equalsTrue() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UserProjection userProjection2 = new UserProjection("username-test", "first-name-test", "last-name-test", 1.0, timestamp, true);
        userProjection.setLast_update(timestamp);
        assertEquals(userProjection, userProjection2);
    }
}