package com.autorun.githubproxy.domain.service;


import com.autorun.githubproxy.domain.controller.TestObjectFactory;
import com.autorun.githubproxy.domain.model.Branch;
import com.autorun.githubproxy.domain.model.Repository;
import com.autorun.githubproxy.domain.model.RepositoryWithBranches;
import com.autorun.githubproxy.domain.rest.client.GithubClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static com.autorun.githubproxy.domain.controller.TestObjectFactory.*;
import static com.autorun.githubproxy.domain.controller.TestObjectFactory.SHA;
import static org.junit.jupiter.api.Assertions.*;


class GitHubConsumerServiceTest {

    private final TestObjectFactory testObjectFactory = new TestObjectFactory();
    private final GithubClient githubClient = Mockito.mock(GithubClient.class);
    private final GitHubConsumerService gitHubConsumerService = new GitHubConsumerService(githubClient);

    @Test
    void shouldNotFindRepositories() throws JsonProcessingException {
        Mockito.when(githubClient.findAllRepositoriesByUserName(USER_NAME)).thenReturn(Collections.emptyList());

        List<RepositoryWithBranches> result = gitHubConsumerService.getNotForkRepositoriesByUserName(USER_NAME);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindRepositories() throws JsonProcessingException {
        List<RepositoryWithBranches> repositories = testObjectFactory.createRepositoriesWithBranches();
        RepositoryWithBranches repository2 = repositories.iterator().next();
        List<Repository> repositoryList = testObjectFactory.createRepositories();
        Repository repository = repositoryList.iterator().next();

        Mockito.when(githubClient.findAllRepositoriesByUserName(USER_NAME)).thenReturn(repositoryList);
        Mockito.when(githubClient.findAllBranchesForRepository(repository)).thenReturn(repository2.getBranches());

        List<RepositoryWithBranches> result = gitHubConsumerService.getNotForkRepositoriesByUserName(USER_NAME);

        assertEquals(1, result.size());

        RepositoryWithBranches resultRepository = result.iterator().next();
        assertEquals(REPOSITORY_NAME, resultRepository.getRepository().getName());
        assertNotNull(resultRepository.getRepository().getOwner());
        assertEquals(USER_NAME, resultRepository.getRepository().getOwner().getLogin());
        assertEquals(1, resultRepository.getBranches().size());
        Branch resultBranch = resultRepository.getBranches().iterator().next();
        assertEquals(BRANCH_NAME, resultBranch.getName());
        assertNotNull(resultBranch.getCommit());
        assertEquals(SHA, resultBranch.getCommit().getSha());
    }

    @Test
    public void shouldExtractForkRepository() throws JsonProcessingException {
        List<Repository> repositories = testObjectFactory.getForkRepository();
        Mockito.when(githubClient.findAllRepositoriesByUserName(USER_NAME)).thenReturn(repositories);

        List<RepositoryWithBranches> result = gitHubConsumerService.getNotForkRepositoriesByUserName(USER_NAME);

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldThrowIllegalStateException() throws JsonProcessingException {
        List<Repository> repositories = testObjectFactory.createRepositories();
        Repository next = repositories.iterator().next();
        Mockito.when(githubClient.findAllRepositoriesByUserName(USER_NAME)).thenReturn(repositories);
        Mockito.when(githubClient.findAllBranchesForRepository(next)).thenThrow(new MockJsonProcessingException("Json processing exception message."));

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            gitHubConsumerService.getNotForkRepositoriesByUserName(USER_NAME);
        });

        Assertions.assertEquals("com.autorun.githubproxy.domain.service.MockJsonProcessingException: Json processing exception message.", exception.getMessage());
    }
}