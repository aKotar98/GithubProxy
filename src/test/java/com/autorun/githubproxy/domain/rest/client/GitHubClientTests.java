package com.autorun.githubproxy.domain.rest.client;

import com.autorun.githubproxy.FileReader;
import com.autorun.githubproxy.domain.controller.TestObjectFactory;
import com.autorun.githubproxy.domain.model.Branch;
import com.autorun.githubproxy.domain.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.autorun.githubproxy.domain.controller.TestObjectFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GitHubClientTests {

    private static final String GITHUB_REPOSITORY_OK_RESPONSE_NAME = "githubRepositoryOkResponse.json";
    private static final String GITHUB_BRANCHES_OK_RESPONSE_NAME = "githubBranchesOkResponse.json";

    @Autowired
    private GithubClient githubClient;

    @Autowired
    private RestTemplate restTemplate;

    private final TestObjectFactory testObjectFactory = new TestObjectFactory();

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        githubClient.setRestTemplate(restTemplate);
    }


    @Test
    void shouldFindRepositoriesByUsername() throws URISyntaxException, IOException {
        String githubRepositoryOkResponse = FileReader.readResponseFromFile(GITHUB_REPOSITORY_OK_RESPONSE_NAME);
        mockServer
                .expect(requestTo(new URI("https://api.github.com/users/user/repos")))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(githubRepositoryOkResponse));

        List<Repository> repositories = githubClient.findAllRepositoriesByUserName(USER_NAME);

        mockServer.verify();
        assertEquals(13, repositories.size());
    }

    @Test
    void shouldFindAllBranchesForRepositoryByRepositoryName() throws URISyntaxException, IOException {
        Repository repository = testObjectFactory.createRepositories().iterator().next();
        String githubRepositoryOkResponse = FileReader.readResponseFromFile(GITHUB_BRANCHES_OK_RESPONSE_NAME);
        mockServer
                .expect(requestTo(new URI("https://api.github.com/repos/user/ArcGame/branches")))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(githubRepositoryOkResponse));

        List<Branch> branches = githubClient.findAllBranchesForRepository(repository);

        mockServer.verify();
        assertEquals(1, branches.size());
    }
}
