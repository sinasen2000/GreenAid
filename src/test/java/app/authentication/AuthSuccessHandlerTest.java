package app.authentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.*;

public class AuthSuccessHandlerTest {

    AuthSuccessHandler auth;
    MockHttpServletRequest request;
    MockHttpServletResponse response;
    Authentication authentication;

    @Before
    public void setUp() {
        auth = new AuthSuccessHandler();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        authentication = new TestingAuthenticationToken(null, null);
    }

    @After
    public void tearDown() {
        auth = null;
        request = null;
        response = null;
        authentication = null;
    }

    @Test
    public void onAuthenticationSuccessStatus() throws IOException, ServletException {
        auth.onAuthenticationSuccess(request, response, authentication);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void onAuthenticationSuccessContentType() throws  IOException, ServletException {
        auth.onAuthenticationSuccess(request, response, authentication);
        assertEquals("Application/JSON", response.getContentType());
    }

    @Test
    public void onAuthenticationSuccessContent() throws  IOException, ServletException {
        auth.onAuthenticationSuccess(request, response, authentication);
        assertEquals("{\"ok\":true,\"data\":\"Logged in.\"}", response.getContentAsString());
    }
}