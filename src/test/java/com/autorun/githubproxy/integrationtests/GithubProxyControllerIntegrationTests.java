package com.autorun.githubproxy.integrationtests;

import com.autorun.githubproxy.FileReader;
import com.autorun.githubproxy.GithubProxyApplication;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = GithubProxyApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubProxyControllerIntegrationTests {

    private static final String SERVER_URL = "http://localhost:";
    private static final String CONTROLLER_PATH = "//github/proxy/repository/";
    private static final String NOT_EXISTING_USER_NAME = "dsfdsdfsfsfeesesef";
    private static final String EXISTING_USER_NAME = "aKotar98";
    private static final String OK_RESPONSE_NAME = "okResponse.json";
    private static final String NOT_FOUND_RESPONSE_NAME = "notFoundResponse.json";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldFindRepositoriesForUser() throws IOException, JSONException {
        //TODO stub request with wiremock to not dependent of external api
        ResponseEntity<String> responseEntity = this.restTemplate
                .getForEntity(SERVER_URL + port + CONTROLLER_PATH + EXISTING_USER_NAME, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String expectedResponse = FileReader.readResponseFromFile(OK_RESPONSE_NAME);
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), true);
    }

    @Test
    void shouldNotFindUser() throws IOException, JSONException {
        //TODO stub request with wiremock to not dependent of external api
        ResponseEntity<String> responseEntity = this.restTemplate
                .getForEntity(SERVER_URL + port + CONTROLLER_PATH + NOT_EXISTING_USER_NAME, String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        String expectedResponse = FileReader.readResponseFromFile(NOT_FOUND_RESPONSE_NAME);
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), true);
    }

}