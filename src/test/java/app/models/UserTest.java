package app.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class UserTest {

    User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setId(1);
        user.setPassword("password");
        user.setUsername("username");
        user.setExperience_points(100);
        user.setFirst_name("first_name");
        user.setLast_name("last_name");
        user.setProfile_picture(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse("2010/10/10");
        user.setLast_update(new Timestamp(date.getTime()));
    }

    @After
    public void tearDown() throws Exception {
        user = null;
    }

    @Test
    public void getId() {
        assertEquals(1, user.getId());
    }

    @Test
    public void setId() {
        user.setId(2);
        assertEquals(2, user.getId());
    }

    @Test
    public void getUsername() {
        assertEquals("username", user.getUsername());
    }

    @Test
    public void setUsername() {
        user.setUsername("changed_username");
        assertEquals("changed_username", user.getUsername());
    }

    @Test
    public void getPassword() {
        assertEquals("password", user.getPassword());
    }

    @Test
    public void setPassword() {
        user.setPassword("changed_password");
        assertEquals("changed_password", user.getPassword());
    }

    @Test
    public void getFirst_name() {
        assertEquals("first_name", user.getFirst_name());
    }

    @Test
    public void setFirst_name() {
        user.setFirst_name("changed_first_name");
        assertEquals("changed_first_name", user.getFirst_name());
    }

    @Test
    public void getLast_name() {
        assertEquals("last_name", user.getLast_name());
    }

    @Test
    public void setLast_name() {
        user.setLast_name("changed_last_name");
        assertEquals("changed_last_name", user.getLast_name());
    }

    @Test
    public void getExperience_points() {
        assertEquals(100, user.getExperience_points(), 0);
    }

    @Test
    public void setExperience_points() {
        user.setExperience_points(200);
        assertEquals(200, user.getExperience_points(), 0);
    }

    @Test
    public void getLast_update() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse("2010/10/10");
        Timestamp expected = new Timestamp(date.getTime());
        assertEquals(expected, user.getLast_update());
    }

    @Test
    public void setLast_update() throws  ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse("2012/12/12");
        Timestamp expected = new Timestamp(date.getTime());
        user.setLast_update(new Timestamp(date.getTime()));
        assertEquals(expected, user.getLast_update());
    }

    @Test
    public void getProfilePicture() {
        assertEquals(1, user.getProfile_picture());
    }

    @Test
    public void setProfilePicture() {
        user.setProfile_picture(2);
        assertEquals(2, user.getProfile_picture());
    }

    @Test
    public void equalsSameUser() {
        assertTrue(user.equals(user));
    }

    @Test
    public void equalsNullObject() {
        assertFalse(user.equals(null));
    }

    @Test
    public void equalsDifferentClass() {
        Object user2 = new String();
        assertFalse(user.equals(user2));
    }

    @Test
    public void equalsDifferentUser() {
        User user2 = new User();
        user2.setId(2);
        assertFalse(user.equals(user2));
    }

    @Test
    public void equalsTrue() {
        User user2 = new User();
        user2.setId(1);
        assertTrue(user.equals(user2));
    }
}