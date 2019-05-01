package app.services;

import app.models.Category;
import app.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @MockBean
    private CategoryRepository categoryRepositoryMock;

    Category category;

    @Before
    public void setUp() {
        category = new Category();
        category.setId(55);
        category.setName("TestCategory");
    }

    @Test
    public void findExistingCategory() {
        Mockito.when(categoryRepositoryMock.findById(55)).thenReturn(category);

        Category result = this.categoryService.findById(55);

        Mockito.verify(categoryRepositoryMock).findById(55);

        Assert.assertEquals(category, result);
    }

    @Test
    public void findNonexistingCategory() {

        Mockito.when(categoryRepositoryMock.findById(55)).thenReturn(null);

        Category result = this.categoryService.findById(55);

        Mockito.verify(categoryRepositoryMock).findById(55);

        Assert.assertNull(result);
    }

    @Test
    public void getCategoryAsText() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        Mockito.when(categoryRepositoryMock.findAll()).thenReturn(categoryList);

        String result = this.categoryService.getCategoryAsText();

        Mockito.verify(categoryRepositoryMock).findAll();

        Assert.assertEquals("\n55 - TestCategory", result);
    }
}
