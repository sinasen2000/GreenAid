package app.client;


import app.models.ActivityProjection;
import app.models.User;
import app.models.UserProjection;
import app.responses.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.parameters.P;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;


@PrepareForTest(HttpRequests.class)
@RunWith(PowerMockRunner.class)
public class ClientTest {

    private static final String url_login = "http://localhost:8080/login";
    private static final String url_add_activity = "http://localhost:8080/user/add-activity";
    private static final String url_remove_activity = "http://localhost:8080/user/remove-activity";
    private static final String url_user_activities = "http://localhost:8080/user/activities";
    private static final String url_user_details = "http://localhost:8080/user/details";
    private static final String url_user_followings = "http://localhost:8080/user/followings";
    private static final String url_user_followed_by = "http://localhost:8080/user/followed-by";
    private static final String url_add_follow = "http://localhost:8080/user/add-following";
    private static final String url_remove_follow = "http://localhost:8080/user/remove-following";
    private static final String url_recommendation = "http://localhost:8080/user/recommendation";
    private static final String url_leaderboard = "http://localhost:8080/user/leaderboard";
    private static final String url_update_profile_picture = "http://localhost:8080/user/update-profile-picture";

    @Test
    public void getSessionCookieFail() {
        String expected = "No cookie found.";

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", "username");
        params.add("password", "password");

        HttpHeaders mockResponseHeaders = new HttpHeaders();
        mockResponseHeaders.set(HttpHeaders.SET_COOKIE, null);

        HttpEntity<Response> mockResponse = new HttpEntity<>(new Response(), mockResponseHeaders);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.postRequest("", url_login, params))
                .thenReturn(mockResponse);

        String result = Client.getSessionCookie("username", "password");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.postRequest("", url_login, params);

        assertEquals(expected, result);
    }

    @Test
    public void getSessionCookieSuccess() {


        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", "username");
        params.add("password", "password");


        HttpHeaders mockResponseHeaders = new HttpHeaders();
        mockResponseHeaders.set(HttpHeaders.SET_COOKIE, "cookieFirstPart;second");

        HttpEntity<Response> mockResponse = new HttpEntity<>(new Response(), mockResponseHeaders);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.postRequest("", url_login, params))
                .thenReturn(mockResponse);


        String sessionCookie = Client.getSessionCookie("username", "password");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.postRequest("", url_login, params);

        assertEquals("cookieFirstPart", sessionCookie);
    }

    @Test
    public void addActivity() {

        long categoryId = 12L;
        double amount = 3.14;

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("category_id", categoryId);
        params.add("amount", amount);

        HttpHeaders mockResponseHeaders = new HttpHeaders();
        HttpEntity<Response> mockResponse = new HttpEntity<>(new Response(), mockResponseHeaders);
        mockResponse.getBody().setData("responseTxt");

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.postRequest("cookie", url_add_activity, params))
                .thenReturn(mockResponse);


        String response = Client.addActivity("cookie", categoryId, amount);

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.postRequest("cookie", url_add_activity, params);

        assertEquals("responseTxt", response);
    }

    @Test
    public void removeActivity() {
        String expected = "test";

        Response response = new Response();
        response.setData("test");
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);


        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.postRequest(eq("sessionCookie"), eq(url_remove_activity), any(MultiValueMap.class)))
                .thenReturn(httpEntity);

        String result = Client.removeActivity("sessionCookie", 1);

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.postRequest(eq("sessionCookie"), eq(url_remove_activity), any(MultiValueMap.class));

        assertEquals(expected, result);
    }

    @Test
    public void getUserActivitiesFail() {

        Response response = new Response();
        response.setOk(false);
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.getRequest("sessionCookie", url_user_activities))
                .thenReturn(httpEntity);

        List<ActivityProjection> result = Client.getUserActivities("sessionCookie");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.getRequest("sessionCookie", url_user_activities);

        assertNull(result);
    }

    @Test
    public void getUserActivitiesSuccess() {

        ObjectMapper mapper = new ObjectMapper();

        List<ActivityProjection> actList = new ArrayList<>();
        ActivityProjection ap = new ActivityProjection(12, "user", "cat", 3.14, 12.1);
        actList.add(ap);
        JsonNode body = mapper.convertValue(actList, JsonNode.class);

        HttpEntity<Response> mockResponse = new HttpEntity<>(new Response(), null);
        mockResponse.getBody().setOk(true);
        mockResponse.getBody().setData(body);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.getRequest("cookie", "http://localhost:8080/user/activities"))
                .thenReturn(mockResponse);

        Object response = Client.getUserActivities("cookie");

        assertEquals(actList, response);

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.getRequest("cookie", "http://localhost:8080/user/activities");
    }

    @Test
    public void getUserDetailsFail() {
        Response response = new Response();
        response.setOk(false);
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.getRequest("sessionCookie", url_user_details))
                .thenReturn(httpEntity);

        User result = Client.getUserDetails("sessionCookie");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.getRequest("sessionCookie", url_user_details);

        assertNull(result);
    }

    @Test
    public void getUserDetailsSuccess() {
        Response response = new Response();
        response.setOk(true);
        User user = new User();
        user.setId(1L);
        response.setData(user);
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.getRequest("sessionCookie", url_user_details))
                .thenReturn(httpEntity);

        User result = Client.getUserDetails("sessionCookie");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.getRequest("sessionCookie", url_user_details);

        assertEquals(user, result);
    }

    @Test
    public void getUserFollowingsFail() {
        Response response = new Response();
        response.setOk(false);
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.getRequest("sessionCookie", url_user_followings))
                .thenReturn(httpEntity);

        List<UserProjection> result = Client.getUserFollowings("sessionCookie");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.getRequest("sessionCookie", url_user_followings);

        assertNull(result);
    }

    @Test
    public void getUserFollowingsSuccess() {
        List<UserProjection> userList = new ArrayList<>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UserProjection userProjection = new UserProjection("username-test", "first-name-test", "last-name-test", 1.0, timestamp, true);
        userList.add(userProjection);
        Response response = new Response();
        response.setOk(true);
        response.setData(userList);
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.getRequest("sessionCookie", url_user_followings))
                .thenReturn(httpEntity);

        List<UserProjection> result = Client.getUserFollowings("sessionCookie");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.getRequest("sessionCookie", url_user_followings);

        assertEquals(userList, result);
    }

    @Test
    public void getUserFollowedByFail() {
        Response response = new Response();
        response.setOk(false);
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.getRequest("sessionCookie", url_user_followed_by))
                .thenReturn(httpEntity);

        List<UserProjection> result = Client.getUserFollowedBy("sessionCookie");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.getRequest("sessionCookie", url_user_followed_by);

        assertNull(result);
    }

    @Test
    public void getUserFollowedBySuccess() {
        List<UserProjection> userList = new ArrayList<>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UserProjection userProjection = new UserProjection("username-test", "first-name-test", "last-name-test", 1.0, timestamp, true);
        userList.add(userProjection);
        Response response = new Response();
        response.setOk(true);
        response.setData(userList);
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.getRequest("sessionCookie", url_user_followed_by))
                .thenReturn(httpEntity);

        List<UserProjection> result = Client.getUserFollowedBy("sessionCookie");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.getRequest("sessionCookie", url_user_followed_by);

        assertEquals(userList, result);
    }

    @Test
    public void manageFollowRequests() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expected = "test";

        Response response = new Response();
        response.setData("test");
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", "username-test");

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.postRequest("sessionCookie", "url-test", params))
                .thenReturn(httpEntity);

        Client client = new Client();
        Class<?> params2[] = new Class[3];
        params2[0] = String.class;
        params2[1] = String.class;
        params2[2] = String.class;
        Object[] args = new String[3];
        args[0] = "sessionCookie";
        args[1] = "username-test";
        args[2] = "url-test";
        Method method = Client.class.getDeclaredMethod("manageFollowRequests", params2);
        method.setAccessible(true);

        String result = (String) (method.invoke(client, args));

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.postRequest("sessionCookie", "url-test", params);

        assertEquals(expected, result);
    }

    @Test
    public void addFollow() {
        String expected = "test";

        Response response = new Response();
        response.setData("test");
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", "username-test");

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.postRequest("sessionCookie", url_add_follow, params))
                .thenReturn(httpEntity);

        String result = Client.addFollow("sessionCookie", "username-test");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.postRequest("sessionCookie", url_add_follow, params);

        assertEquals(expected, result);
    }

    @Test
    public void removeFollow() {
        String expected = "test";

        Response response = new Response();
        response.setData("test");
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", "username-test");

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.postRequest("sessionCookie", url_remove_follow, params))
                .thenReturn(httpEntity);

        String result = Client.removeFollow("sessionCookie", "username-test");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.postRequest("sessionCookie", url_remove_follow, params);

        assertEquals(expected, result);
    }

    @Test
    public void getRecommendation() {
        String expected = "test";

        Response response = new Response();
        response.setData("test");
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.getRequest("sessionCookie", url_recommendation))
                .thenReturn(httpEntity);

        String result = Client.getRecommendation("sessionCookie");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.getRequest("sessionCookie", url_recommendation);

        assertEquals(expected, result);
    }

    @Test
    public void getLeaderboardFail() {
        Response response = new Response();
        response.setOk(false);
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.getRequest("sessionCookie", url_leaderboard))
                .thenReturn(httpEntity);

        List<UserProjection> result = Client.getLeaderboard("sessionCookie");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.getRequest("sessionCookie", url_leaderboard);

        assertNull(result);
    }

    @Test
    public void getLeaderboardSuccess() {
        Response response = new Response();
        response.setOk(true);

        List<UserProjection> userList = new ArrayList<>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UserProjection userProjection = new UserProjection("username-test", "first-name-test", "last-name-test", 1.0, timestamp, true);
        userList.add(userProjection);
        response.setData(userList);
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.getRequest("sessionCookie", url_leaderboard))
                .thenReturn(httpEntity);

        List<UserProjection> result = Client.getLeaderboard("sessionCookie");

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.getRequest("sessionCookie", url_leaderboard);

        assertEquals(userList, result);
    }

    @Test
    public void updateProfilePicture() {
        String expected = "data-test";

        Response response = new Response();
        response.setOk(true);
        response.setData("data-test");
        HttpEntity<Response> httpEntity = new HttpEntity<>(response);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("profile_picture", 1);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.postRequest("sessionCookie", url_update_profile_picture, params))
                .thenReturn(httpEntity);

        String result = Client.updateProfilePicture("sessionCookie", 1);

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.postRequest("sessionCookie", url_update_profile_picture, params);

        assertEquals(expected, result);
    }
}