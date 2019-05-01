package app.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * finds the username of the logged in user.
     * @return returns the username if found, null otherwise
     */
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication();
        if (userDetails != null) {
            userDetails = ((Authentication) userDetails).getPrincipal();
            if (userDetails instanceof UserDetails) {
                return ((UserDetails) userDetails).getUsername();
            }
        }
        return null;
    }

    /**
     * method that enables login and authentication.
     * @param username username of the user
     * @param password password of the user
     */
    public void autoLogin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,
                        password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);
        logger.debug(String.format("Auto login %s successfully!", username));
    }
}
