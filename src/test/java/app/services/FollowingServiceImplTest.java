package app.services;

import app.models.Following;
import app.repository.FollowingRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FollowingServiceImplTest {

    @Autowired
    FollowingServiceImpl followingService;

    @MockBean
    FollowingRepository followingRepositoryMock;

    Following following;

    @Before
    public void setUp() {
        following  = new Following();
        following.setId(1);
        following.setUser_id_1(1);
        following.setUser_id_2(2);
    }

    @Test
    public void saveFollowing() {
        Mockito.when(followingRepositoryMock.save(following)).thenReturn(following);

        this.followingService.save(following);

        Mockito.verify(followingRepositoryMock).save(following);
    }

    @Test
    public void deleteFollowing() {
        Mockito.doAnswer((i) -> {
            Assert.assertEquals(following, i.getArgument(0));
            return null;
        }).when(followingRepositoryMock).delete(following);

        this.followingService.delete(following);

        Mockito.verify(followingRepositoryMock).delete(following);
    }

    @Test
    public void findById1Id2() {
        Mockito.when(followingRepositoryMock.findById1Id2(1, 2)).thenReturn(following);

        Following result = followingService.findById1Id2(1, 2);

        Mockito.verify(followingRepositoryMock).findById1Id2(1,2);

        Assert.assertEquals(following, result);
    }

}
