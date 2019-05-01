package app.authentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.*;

public class AuthFailureHandlerTest {

    AuthFailureHandler auth;
    MockHttpServletRequest request;
    MockHttpServletResponse response;
    AuthenticationException exception;

    @Before
    public void setUp() {
        auth = new AuthFailureHandler();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        exception = new AuthenticationServiceException("Exception");
    }

    @After
    public void tearDown() {
        auth = null;
        request = null;
        response = null;
        exception = null;
    }

    @Test
    public void onAuthenticationFailureStatus() throws IOException, ServletException {
        auth.onAuthenticationFailure(request, response, exception);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void onAuthenticationFailureContentType() throws  IOException, ServletException {
        auth.onAuthenticationFailure(request, response, exception);
        assertEquals("Application/JSON", response.getContentType());
    }

    @Test
    public void onAuthenticationFailureContent() throws  IOException, ServletException {
        auth.onAuthenticationFailure(request, response, exception);
        assertEquals("{\"ok\":false,\"data\":\"Auth failure\"}", response.getContentAsString());
    }
}