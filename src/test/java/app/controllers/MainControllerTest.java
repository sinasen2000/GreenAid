package app.controllers;

import app.authentication.SecurityServiceImpl;
import app.responses.Response;
import app.services.CategoryServiceImpl;
import app.services.UserServiceImpl;
import app.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MainControllerTest {

    @Autowired
    private MainController mainController;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private SecurityServiceImpl securityService;

    @MockBean
    private CategoryServiceImpl categoryService;

    @Test
    public void welcomeTest() {
        Response expected = new Response( true, "welcome");

        Response result = mainController.welcome();

        assertEquals(expected, result);
    }

    @Test
    public void checkAuthFail() {
        Response expected = new Response(false, "User not logged in");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = mainController.checkAuth();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void checkAuthSuccess() {
        Response expected = new Response(true, "Your username is:username-test");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");

        Response result = mainController.checkAuth();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void registrationFail() {
        Response expected = new Response(false, "Username is already registered");

        User user = new User();
        user.setUsername("username-test");

        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);

        Response result = mainController.registration(user);

        Mockito.verify(userService).findByUsername("username-test");

        assertEquals(expected, result);
    }

    @Test
    public void registrationSuccess() {
        Response expected = new Response(true, "You are now registered, username-test!");

        User user = new User();
        user.setUsername("username-test");

        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(null);
        Mockito.doAnswer((i) -> null).when(userService).save(any(User.class));

        Response result = mainController.registration(user);

        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(userService).save(any(User.class));

        assertEquals(expected, result);
    }

    @Test
    public void getCategoriesFail() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = mainController.getCategories();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void getCategoriesSuccess() {
        Response expected = new Response(true, "\n1 - category-test");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(categoryService.getCategoryAsText())
                .thenReturn("\n1 - category-test");

        Response result = mainController.getCategories();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(categoryService).getCategoryAsText();

        assertEquals(expected.getData(), result.getData());
    }
}
