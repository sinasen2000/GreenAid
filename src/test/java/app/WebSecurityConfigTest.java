package app;

import app.authentication.SecurityServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebSecurityConfigTest {

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Test
    public void test() {
        assertTrue(true);
    }

    @Test
    public void bCryptPasswordEncoder() {
        BCryptPasswordEncoder hashedPassword;
        hashedPassword = webSecurityConfig.bCryptPasswordEncoder();
        assertNotNull(hashedPassword);
    }

    @Test
    public void userDetailsService() {
        UserDetailsService userDetailsService;
        userDetailsService = webSecurityConfig.userDetailsService();
        assertNotNull(userDetailsService);
    }

    @Test
    public void customAuthenticationManager() throws Exception{
        //        securityService.autoLogin("cpene", "cpenecpene");
        AuthenticationManager authenticationManager;
        authenticationManager = webSecurityConfig.customAuthenticationManager();
        assertNotNull(authenticationManager);
    }
}