package app.authentication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityContextHolder.class)
public class SecurityServiceImplTest {

    @InjectMocks
    private SecurityServiceImpl securityService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void findLoggedInUsernameFail() {
        SecurityContext securityContext = new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                return null;
            }

            @Override
            public void setAuthentication(Authentication authentication) {

            }
        };

        PowerMockito.mockStatic(SecurityContextHolder.class);
        PowerMockito.when(SecurityContextHolder.getContext())
                .thenReturn(securityContext);

        String result = securityService.findLoggedInUsername();

        PowerMockito.verifyStatic(SecurityContextHolder.class);
        SecurityContextHolder.getContext();

        assertEquals(null, result);
    }

    @Test
    public void findLoggedInUsernameNotInstanceOfUserDetails() {
        SecurityContext securityContext = new SecurityContext() {
            private Authentication authentication;
            @Override
            public Authentication getAuthentication() {
                return this.authentication;
            }

            @Override
            public void setAuthentication(Authentication authentication) {
                this.authentication = authentication;
            }
        };
        Authentication authentication = new UsernamePasswordAuthenticationToken("principal-test", "password-test");

        securityContext.setAuthentication(authentication);

        PowerMockito.mockStatic(SecurityContextHolder.class);
        PowerMockito.when(SecurityContextHolder.getContext())
                .thenReturn(securityContext);

        String result = securityService.findLoggedInUsername();

        PowerMockito.verifyStatic(SecurityContextHolder.class);
        SecurityContextHolder.getContext();

        assertEquals(null, result);
    }

    @Test
    public void findLoggedInUsernameSuccess() {
        SecurityContext securityContext = new SecurityContext() {
            private Authentication authentication;
            @Override
            public Authentication getAuthentication() {
                return this.authentication;
            }

            @Override
            public void setAuthentication(Authentication authentication) {
                this.authentication = authentication;
            }
        };
        UserDetails user = User.withUsername("username-test")
                .password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
                .roles("USER")
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, "password-test");

        securityContext.setAuthentication(authentication);

        PowerMockito.mockStatic(SecurityContextHolder.class);
        PowerMockito.when(SecurityContextHolder.getContext())
                .thenReturn(securityContext);

        String result = securityService.findLoggedInUsername();

        PowerMockito.verifyStatic(SecurityContextHolder.class);
        SecurityContextHolder.getContext();

        assertEquals("username-test", result);
    }

    @Test
    public void autoLogin(){
        UserDetails user = User.withUsername("username-test")
                .password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
                .roles("USER")
                .build();

        Mockito.when(userDetailsService.loadUserByUsername(any(String.class)))
                .thenReturn(user);
        Mockito.doAnswer((i) -> null).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        securityService.autoLogin("username-test", "password-test");

        Mockito.verify(userDetailsService).loadUserByUsername(any(String.class));
        Mockito.verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertEquals("username-test", securityService.findLoggedInUsername());
    }
}