package org.home;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

public class WebAwareClient {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseExtractor<Object> soutResponse = response -> {
                System.out.println(new String(response.getBody().readAllBytes()));
                return null;
            };

        RequestCallback printRequest = request -> System.out.println("Request for: " + request.getURI());

        restTemplate.execute("http://localhost:8080/test/scopes/request",
            HttpMethod.GET,
            printRequest,
            soutResponse
        );

        restTemplate.execute("http://localhost:8080/test/scopes/session",
            HttpMethod.GET,
            printRequest,
            soutResponse
        );

        restTemplate.execute("http://localhost:8080/test/scopes/application",
            HttpMethod.GET,
            printRequest,
            soutResponse
        );
    }
}
