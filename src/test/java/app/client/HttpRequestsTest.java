package app.client;

import app.client.HttpRequests;
import app.responses.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8080")
public class HttpRequestsTest {

    HttpRequests httpRequests;

    private static final String username = "johnDoe";
    private static final String password = "verySecret";

    @Test
    public void constructorDefault() {
        httpRequests = new HttpRequests();
        assertNotNull(httpRequests);
    }

    @Test
    public void setHeaders() {
        String expected = "[Accept:\"application/json\", Content-Type:\"application/x-www-form-urlencoded\", Cookie:\"sessionCookie\"]";
        HttpHeaders headers = HttpRequests.setHeaders("sessionCookie");
        assertEquals(expected, headers.toString());
    }


    @Test
    public void getRequest() {
        String URL = "http://localhost:8080/welcome";
        String sessionCookie = "sessionCookie";

        HttpEntity<Response> response = HttpRequests.getRequest(sessionCookie, URL);
        assertEquals(true, response.getBody().isOk());
    }

    @Test
    public void postRequest() {
        String registrationURL = "http://localhost:8080/registration";
        String registrationSessionCookie = "sessionCookie";
        MultiValueMap<String, Object> registrationParams = new LinkedMultiValueMap<>();

        registrationParams.add("first_name", "John");
        registrationParams.add("last_name", "Doe");
        registrationParams.add("username", username);
        registrationParams.add("password", password);

        HttpRequests.postRequest(registrationSessionCookie, registrationURL, registrationParams);

        String URL = "http://localhost:8080/login";
        String sessionCookie = "sessionCookie";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);
        HttpEntity<Response> response = HttpRequests.postRequest(sessionCookie, URL, params);
        assertTrue( response.getBody().isOk());
    }
}