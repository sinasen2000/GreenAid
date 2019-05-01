package app.authentication;

import app.responses.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Custom authentication failure handler.
 */
public class AuthFailureHandler implements AuthenticationFailureHandler {

    /**
     * Handles authentication failure.
     * @param request HTTP request
     * @param response HTTP response
     * @param exception Exception that caused the authentication failure
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        response.setStatus(200);
        response.setContentType("Application/JSON");
        response.getWriter().write(
                new ObjectMapper().writer().writeValueAsString(
                        new Response(false, "Auth failure"))
        );
    }

}
