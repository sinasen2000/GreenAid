package app.responses;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseTest {

    private Response response;

    @Before
    public void setUp() {
        response = new Response();
    }

    @After
    public void tearDown() {
        response = null;
    }

    @Test
    public void constructorWithoutParametersNotNull() {
        response = new Response();
        assertNotNull(response);
    }

    @Test
    public void constructorWithoutParametersSetDefaultValuesOk() {
        response = new Response();
        assertFalse(response.isOk());
    }

    @Test
    public void constructorWithoutParametersSetDefaultValuesData() {
        response = new Response();
        assertEquals(null, response.getData());
    }


    @Test
    public void constructorWithParametersOk() {
        String data = "data";
        response = new Response(true, data);
        assertTrue(response.isOk());
    }

    @Test
    public void constructorWithParametersData() {
        String data = "data";
        response = new Response(true, data);
        assertEquals(data, response.getData());
    }

    @Test
    public void isOk() {
        assertFalse(response.isOk());
    }

    @Test
    public void setOk() {
        response.setOk(true);
        assertTrue(response.isOk());
    }

    @Test
    public void getData() {
        assertNull(response.getData());
    }

    @Test
    public void setData() {
        response.setData("data");
        assertEquals("data", response.getData());
    }

    @Test
    public void equalsSameObject() {
        assertEquals(response, response);
    }

    @Test
    public void equalsNullObject() {
        assertNotEquals(response, null);
    }

    @Test
    public void equalsDifferentClass() {
        assertNotEquals(response, new String());
    }

    @Test
    public void equalsDifferentOk() {
        Response response2 = new Response(false, "data-test");
        response = new Response(true, "data-test");
        assertNotEquals(response, response2);
    }

    @Test
    public void equalsDifferentData() {
        Response response2 = new Response(true, "data-test-2");
        response = new Response(true, "data-test");
        assertNotEquals(response, response2);
    }

    @Test
    public void equalsTrue() {
        Response response2 = new Response(true, "data-test");
        response = new Response(true, "data-test");
        assertEquals(response, response2);
    }
}