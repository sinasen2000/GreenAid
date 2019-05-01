package app.authentication;

import app.responses.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Custom authentication success handler.
 */
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Handles successful authentication attempts.
     * @param request HTTP request
     * @param response HTTP response
     * @param authentication Authentication
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        response.setStatus(200);
        response.setContentType("Application/JSON");
        response.getWriter().write(new ObjectMapper().writer().writeValueAsString(
                new Response(true, "Logged in.")));
    }

}
