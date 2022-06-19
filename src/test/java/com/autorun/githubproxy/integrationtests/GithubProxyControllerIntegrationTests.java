package com.autorun.githubproxy.integrationtests;

import com.autorun.githubproxy.FileReader;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class GithubProxyControllerIntegrationTests {

    private static final String CONTROLLER_PATH = "/github/proxy/repository/";
    private static final String NOT_EXISTING_USER_NAME = "dsfdsdfsfsfeesesef";
    private static final String EXISTING_USER_NAME = "aKotar98";
    private static final String OK_RESPONSE_NAME = "okResponse.json";
    private static final String NOT_FOUND_RESPONSE_NAME = "notFoundResponse.json";
    private static final String GITHUB_SINGLE_REPOSITORY_OK_RESPONSE_NAME = "githubSingleRepositoryOkResponse.json";
    private static final String GITHUB_BRANCHES_OK_RESPONSE_NAME = "githubBranchesOkResponse.json";
    private static final String REPOSITORY_NAME = "EkoGra";

    @Autowired
    private MockMvc mockMvc;

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void init() {
        wireMockServer = new WireMockServer(
                new WireMockConfiguration().port(7075)
        );
        wireMockServer.start();
        WireMock.configureFor("localhost", 7075);
    }

    @Test
    void shouldFindRepositoriesForUser() throws Exception {
        String repositoryResponse = FileReader.readResponseFromFile(GITHUB_SINGLE_REPOSITORY_OK_RESPONSE_NAME);
        stubFor(WireMock.get(urlEqualTo(String.format("/users/%s/repos", EXISTING_USER_NAME)))
                .willReturn(ok().withStatus(200).withBody(repositoryResponse)));
        String branchesResponse = FileReader.readResponseFromFile(GITHUB_BRANCHES_OK_RESPONSE_NAME);
        stubFor(WireMock.get(urlEqualTo(String.format("/repos/%s/%s/branches", EXISTING_USER_NAME, REPOSITORY_NAME)))
                .willReturn(ok().withStatus(200).withBody(branchesResponse)));
        String expectedResponse = FileReader.readResponseFromFile(OK_RESPONSE_NAME);

        this.mockMvc.perform(get(CONTROLLER_PATH + EXISTING_USER_NAME))
                .andExpect(status().is(200))
                .andExpect(content().json((expectedResponse)));
    }


    @Test
    void shouldNotFindUser() throws Exception {
        stubFor(WireMock.get(urlMatching(String.format("/users/%s/repos", NOT_EXISTING_USER_NAME)))
                .willReturn(notFound()));
        String expectedResponse = FileReader.readResponseFromFile(NOT_FOUND_RESPONSE_NAME);

        this.mockMvc.perform(get(CONTROLLER_PATH + NOT_EXISTING_USER_NAME))
                .andExpect(status().is(404)).andExpect(content().json(expectedResponse));
    }
}

