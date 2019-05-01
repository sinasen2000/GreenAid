package app.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategoryTest {

    private final static double DELTA = 0;
    private Category category;

    @Before
    public void setUp() {
        category = new Category("name-test", 1, 1.0, 1);
    }

    @After
    public void tearDown() {
        category = null;
    }

    @Test
    public void constructorDefault() {
        category = new Category();
        assertNotNull(category);
    }

    @Test
    public void constructorParameters() {
        category = new Category("name-test", 1, 1.0, 1);
        assertNotNull(category);
    }

    @Test
    public void getId() {
        assertEquals(0, category.getId());
    }

    @Test
    public void setId() {
        category.setId(2);
        assertEquals(2, category.getId());
    }

    @Test
    public void getName() {
        assertEquals("name-test", category.getName());
    }

    @Test
    public void setName() {
        category.setName("name-test-2");
        assertEquals("name-test-2", category.getName());
    }

    @Test
    public void getAmount_saved() {
        assertEquals(1, category.getAmount_saved());
    }

    @Test
    public void setAmount_saved() {
        category.setAmount_saved(2);
        assertEquals(2, category.getAmount_saved());
    }

    @Test
    public void getXp_points() {
        assertEquals(1.0, category.getXp_points(), DELTA);
    }

    @Test
    public void setXp_points() {
        category.setXp_points(2.0);
        assertEquals(2.0, category.getXp_points(), DELTA);
    }

    @Test
    public void getCategory_type_id() {
        assertEquals(1, category.getCategory_type_id());
    }

    @Test
    public void setCategory_type_id() {
        category.setCategory_type_id(2);
        assertEquals(2, category.getCategory_type_id());
    }
}
