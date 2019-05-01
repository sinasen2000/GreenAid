package app.client;

import app.responses.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class HttpRequests {

    /**
     * Sets headers for the communication between server and client.
     * @param sessionCookie uses the sessionCookie to achieve it
     * @return returns the headers of the communication
     */
    public static HttpHeaders setHeaders(String sessionCookie) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Cookie", sessionCookie);

        return headers;
    }

    /**
     *Method that creates the GET request of the communication.
     * @param sessionCookie uses sessionCookie to achieve it.
     * @param url url to be mapped with the get request
     * @return returns the url with the given request.
     */
    public static HttpEntity<Response> getRequest(String sessionCookie, String url) {

        HttpHeaders headers = setHeaders(sessionCookie);
        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, request, Response.class);

    }

    /**
     * Method that creates the POST request of the communication.
     * @param sessionCookie uses sessionCookie to achieve it.
     * @param url url to be mapped with the post request
     * @param params parameters of the post request (i.e what will be changed)
     * @return returns the url with the given request.
     */
    public static HttpEntity<Response> postRequest(
            String sessionCookie, String url, MultiValueMap<String, Object> params) {

        HttpHeaders headers = setHeaders(sessionCookie);
        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);

        return restTemplate.exchange(url, HttpMethod.POST, request, Response.class);

    }

}
