package com.autorun.githubproxy.integrationtests;

import com.autorun.githubproxy.GithubProxyApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = GithubProxyApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubProxyControllerIntegrationTests {

    private static final String SERVER_URL = "http://localhost:";
    private static final String PATH = "//github/proxy/repository/";
    private static final String NOT_EXISTING_USER = "dsfdsdfsfsfeesesef";
    private static final String EXISTING_USER = "aKotar98";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldFindRepositoriesForUser() {
        ResponseEntity<String> responseEntity = this.restTemplate
                .getForEntity(SERVER_URL + port + PATH + EXISTING_USER, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //TODO JSON body compare
    }

    @Test
    public void shouldNotFindUser() {
        ResponseEntity<String> responseEntity = this.restTemplate
                .getForEntity(SERVER_URL + port + PATH + NOT_EXISTING_USER, String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        //TODO JSON body compare
    }
}