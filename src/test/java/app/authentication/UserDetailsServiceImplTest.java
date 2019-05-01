package app.authentication;

import app.models.User;
import app.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepositoryMock;

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameNotFoundException() {
        Mockito.when(userRepositoryMock.findByUsername("username-test")).thenReturn(null);
        userDetailsService.loadUserByUsername("username-test");
    }

    @Test
    public void loadUserByUsernameSuccess() {

        User user = new User();
        user.setUsername("username-test");
        user.setPassword("password-test");

        Mockito.when(userRepositoryMock.findByUsername("username-test")).thenReturn(user);

        UserDetails result = userDetailsService.loadUserByUsername("username-test");

        Mockito.verify(userRepositoryMock).findByUsername("username-test");

        Assert.assertEquals("username-test", result.getUsername());
    }

    @Test
    public void getAuthorities(){
        Collection<? extends GrantedAuthority> authorities = userDetailsService.getAuthorities();
        Assert.assertEquals(0, authorities.size());
    }
}
