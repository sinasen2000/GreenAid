package app.services;

import app.authentication.SecurityServiceImpl;
import app.models.Following;
import app.models.User;
import app.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoderMock;

    @MockBean
    private SecurityServiceImpl securityServiceMock;

    @MockBean
    private FollowingServiceImpl followingServiceMock;

    @MockBean
    private UserRepository userRepositoryMock;

    User user;

    @Before
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("username-test");
        user.setPassword("password");
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void saveUser() {
        String encodedPassword = "encoded";
        String originalPassword = user.getPassword();

        Mockito.when(bCryptPasswordEncoderMock.encode(user.getPassword())).thenReturn(encodedPassword);
        Mockito.when(userRepositoryMock.save(user)).thenReturn(user);

        this.userService.save(user);

        Mockito.verify(bCryptPasswordEncoderMock).encode(originalPassword);
        Mockito.verify(userRepositoryMock).save(user);
    }

    @Test
    public void saveUserEncryption() {
        String encodedPassword = "encoded";
        String originalPassword = user.getPassword();

        Mockito.when(bCryptPasswordEncoderMock.encode(user.getPassword())).thenReturn(encodedPassword);
        Mockito.when(userRepositoryMock.save(user)).thenReturn(user);

        this.userService.save(user);

        Mockito.verify(bCryptPasswordEncoderMock).encode(originalPassword);
        Mockito.verify(userRepositoryMock).save(user);

        Assert.assertEquals(encodedPassword, user.getPassword());
    }

    @Test
    public void deleteExistingUser() {
        Mockito.when(userRepositoryMock.findByUsername("username-test")).thenReturn(user);

        Mockito.doAnswer((i) -> {
            Assert.assertEquals(user, i.getArgument(0));
            return null;
        }).when(userRepositoryMock).delete(user);

        this.userService.delete(user);

        Mockito.verify(userRepositoryMock).findByUsername("username-test");
        Mockito.verify(userRepositoryMock).delete(user);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void deleteNonexistingUser() {
        Mockito.when(userRepositoryMock.findByUsername("username-test")).thenReturn(null);

        this.userService.delete(user);

        Mockito.verify(userRepositoryMock).findByUsername("username-test");
    }

    @Test
    public void findByUsername() {
        Mockito.when(userRepositoryMock.findByUsername("username-test")).thenReturn(user);

        User expectedUser =  userService.findByUsername("username-test");

        Mockito.verify(userRepositoryMock).findByUsername("username-test");

        Assert.assertEquals(user, expectedUser);
    }

    @Test
    public void getExistingLoggedInUser() {
        Mockito.when(securityServiceMock.findLoggedInUsername()).thenReturn("username-test");
        Mockito.when(userRepositoryMock.findByUsername("username-test")).thenReturn(user);

        User expectedUser = userService.getLoggedInUser();

        Mockito.verify(securityServiceMock).findLoggedInUsername();
        Mockito.verify(userRepositoryMock).findByUsername("username-test");

        Assert.assertEquals(user, expectedUser);
    }

    @Test
    public void getNonexistingLoggedInUser() {
        Assert.assertEquals(null, userService.getLoggedInUser());
    }

    @Test
    public void findFollowings() {
        Following following1 = new Following();
        following1.setId(1L);
        following1.setUser_id_1(1L);
        following1.setUser_id_2(2L);

        Following following2 = new Following();
        following2.setId(2L);
        following2.setUser_id_1(1L);
        following2.setUser_id_2(3L);

        List followList = new ArrayList();
        followList.add(following1);
        followList.add(following2);

        Mockito.when(userRepositoryMock.findFollowings(1)).thenReturn(followList);

        List result = userService.findFollowings(1);

        Mockito.verify(userRepositoryMock).findFollowings(1);

        Assert.assertEquals(followList, result);
    }

    @Test
    public void findFollowers() {
        Following following1 = new Following();
        following1.setId(1L);
        following1.setUser_id_1(1L);
        following1.setUser_id_2(3L);

        Following following2 = new Following();
        following2.setId(2L);
        following2.setUser_id_1(2L);
        following2.setUser_id_2(3L);

        List followList = new ArrayList();
        followList.add(following1);
        followList.add(following2);

        Mockito.when(userRepositoryMock.findFollowedBy(3)).thenReturn(followList);

        List result = userService.findFollowedBy(3);

        Mockito.verify(userRepositoryMock).findFollowedBy(3);

        Assert.assertEquals(followList, result);
    }

    @Test
    public void findLeaderboard() {
        List userList = new ArrayList();
        userList.add(user);

        Mockito.when(userRepositoryMock.findLeaderboard()).thenReturn(userList);

        Assert.assertEquals(userList, userService.findLeaderboard());
    }

    @Test
    public void addFollowingUserNotFound() {
        String expected = "User not found.";
        
        Mockito.when(userRepositoryMock.findByUsername("username-test"))
                .thenReturn(null);

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(expected);
        userService.addFollowing(new Following(), "username-test");
    }

    @Test
    public void addFollowingAlreadyFollowing() {
        String expected = "You already follow this user!";

        User user2 = new User();
        user2.setId(2);

        Following following = new Following();
        Following following2 = new Following();

        Mockito.when(userRepositoryMock.findByUsername("username-test2")).thenReturn(user2);
        Mockito.when(securityServiceMock.findLoggedInUsername()).thenReturn("username-test");
        Mockito.when(userRepositoryMock.findByUsername("username-test")).thenReturn(user);
        Mockito.when(followingServiceMock.findById1Id2(1,2)).thenReturn(following2);

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(expected);
        userService.addFollowing(following, "username-test2");
    }

    @Test
    public void addFollowingRecursiveFollow() {
        String expected = "You already follow yourself...";

        Mockito.when(userRepositoryMock.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(securityServiceMock.findLoggedInUsername())
                .thenReturn("username-test");

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(expected);
        userService.addFollowing(new Following(), "username-test");
    }

    @Test
    public void addFollowingSuccess() {
        String expected = "Your followings have been updated!";

        User user2 = new User();
        user2.setId(2);

        Following following = new Following();

        Mockito.when(userRepositoryMock.findByUsername("username-test2"))
                .thenReturn(user2);
        Mockito.when(securityServiceMock.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(followingServiceMock.findById1Id2(1,2)).thenReturn(null);
        Mockito.when(userRepositoryMock.findByUsername("username-test"))
                .thenReturn(user);

        Mockito.doAnswer((i) -> null).when(followingServiceMock).save(any(Following.class));

        String result = userService.addFollowing(following, "username-test2");

        Mockito.verify(userRepositoryMock, times(2)).findByUsername("username-test2");
        Mockito.verify(securityServiceMock, Mockito.times(3)).findLoggedInUsername();
        Mockito.verify(followingServiceMock).findById1Id2(1,2);
        Mockito.verify(userRepositoryMock, times(2)).findByUsername("username-test");

        assertEquals(expected, result);
    }

    @Test
    public void removeFollowingUserNotFound() {
        String expected = "User not found.";

        Mockito.when(securityServiceMock.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userRepositoryMock.findByUsername("username-test"))
                .thenReturn(null);

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(expected);
        userService.removeFollowing("username-test");
    }

    @Test
    public void removeFollowingRecursiveUnfollow() {
        String expected = "You cannot unfollow yourself...";

        Mockito.when(securityServiceMock.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userRepositoryMock.findByUsername("username-test"))
                .thenReturn(user);

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(expected);
        userService.removeFollowing("username-test");
    }

    @Test
    public void removeFollowingSuccess() {
        String expected = "Your followings have been updated!";

        User user2 = new User();
        user2.setId(2);

        Mockito.when(securityServiceMock.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userRepositoryMock.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(userRepositoryMock.findByUsername("username-test2"))
                .thenReturn(user2);
        Mockito.doAnswer((i) -> null).when(followingServiceMock).delete(any(Following.class));
        Mockito.when(followingServiceMock.findById1Id2(1, 2))
                .thenReturn(new Following());

        String result = userService.removeFollowing("username-test2");

        Mockito.verify(securityServiceMock, times(2)).findLoggedInUsername();
        Mockito.verify(userRepositoryMock).findByUsername("username-test");
        Mockito.verify(userRepositoryMock, times(2)).findByUsername("username-test2");
        Mockito.verify(followingServiceMock).delete(any(Following.class));
        Mockito.verify(followingServiceMock).findById1Id2(1, 2);

        assertEquals(expected, result);
    }

    @Test
    public void updateProfilePicture() {
        Mockito.doAnswer((i) -> null).when(userRepositoryMock).updateProfilePicture(1, 2);

        userService.updateProfilePicture(1, 2);

        Mockito.verify(userRepositoryMock).updateProfilePicture(1, 2);
    }
}