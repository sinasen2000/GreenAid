package app.controllers;

import app.authentication.SecurityServiceImpl;
import app.models.User;
import app.responses.Response;
import app.services.CategoryServiceImpl;
import app.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;


@RestController
public class MainController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private CategoryServiceImpl categoryService;


    /**
     * Routes to /welcome.
     * @return returns the given response.
     */
    @GetMapping("/welcome")
    public Response welcome() {

        return new Response( true, "welcome");
    }

    /**
     * Routes to /check.
     * @return returns the given response to check the authentication
     */
    @GetMapping("/check")
    public Response checkAuth() {
        String username = securityService.findLoggedInUsername();
        if (username != null) {
            return new Response(true, "Your username is:" + username);
        } else {
            return new Response(false, "User not logged in");
        }
    }

    /**
     * Get request for Mapping to /registration of app.
     * @param user that we will be using
     * @return returns registration
     */
    @PostMapping("/registration")
    public Response registration(User user) {

        if (userService.findByUsername(user.getUsername()) != null) {
            return new Response(false, "Username is already registered");
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            user.setLast_update(timestamp);
            userService.save(user);
            return new Response(true, "You are now registered, " + user.getUsername() + "!");
        }

    }

    /**
     * Creates a GET request based mapping to /getcategories.
     * @return returns the categories
     */
    @GetMapping("/getcategories")
    public Response getCategories() {

        String username = securityService.findLoggedInUsername();
        if (username != null) {
            String response = categoryService.getCategoryAsText();

            return new Response(true, response);
        } else {
            return new Response(false, "You are not authorized!");
        }
    }

}
