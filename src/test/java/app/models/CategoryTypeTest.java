package app.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTypeTest {

    private CategoryType categoryType;

    @Before
    public void setUp() {
        categoryType = new CategoryType("category-name");
    }

    @After
    public void tearDown() {
        categoryType = null;
    }

    @Test
    public void getId() {
        assertEquals(0, categoryType.getId());
    }

    @Test
    public void constructorDefault() {
        categoryType = new CategoryType();
        assertNotNull(categoryType);
    }

    @Test
    public void constructorParameters() {
        categoryType = new CategoryType("category-name");
        assertNotNull(categoryType);
    }

    @Test
    public void setId() {
        categoryType.setId(2);
        assertEquals(2, categoryType.getId());
    }

    @Test
    public void getCategory_name() {
        assertEquals("category-name", categoryType.getCategory_name());
    }

    @Test
    public void setCategory_name() {
        categoryType.setCategory_name("category-name-2");
        assertEquals("category-name-2", categoryType.getCategory_name());
    }
}