package com.autorun.githubproxy.domain.controller;

import com.autorun.githubproxy.domain.service.GitHubConsumerService;
import com.autorun.githubproxy.dto.BranchDTO;
import com.autorun.githubproxy.dto.GitHubProxyResponseDTO;
import com.autorun.githubproxy.dto.RepositoryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.autorun.githubproxy.domain.controller.TestObjectFactory.*;
import static com.autorun.githubproxy.domain.controller.TestObjectFactory.SHA;
import static org.junit.jupiter.api.Assertions.*;

class GithubProxyControllerTest {

    private final GitHubConsumerService gitHubConsumerService = Mockito.mock(GitHubConsumerService.class);
    private final ResponseMapper responseMapper = new ResponseMapper();
    private final TestObjectFactory testObjectFactory = new TestObjectFactory();
    private final GithubProxyController githubProxyController = new GithubProxyController(gitHubConsumerService,responseMapper);

    @Test
    void shouldReturnRepositories() throws JsonProcessingException {
        Mockito.when(gitHubConsumerService.getNotForkRepositoriesByUserName(USER_NAME)).thenReturn(testObjectFactory.createRepositoriesWithBranches());
        ResponseEntity<GitHubProxyResponseDTO> response = githubProxyController.getRepositoryNameByUsername(USER_NAME);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getRepositories().size());
        RepositoryDTO repositoryDTO = response.getBody().getRepositories().iterator().next();
        assertEquals(REPOSITORY_NAME, repositoryDTO.getName());
        assertEquals(USER_NAME, repositoryDTO.getOwner());
        assertEquals(1, repositoryDTO.getBranches().size());
        BranchDTO branchDTO = repositoryDTO.getBranches().iterator().next();
        assertEquals(BRANCH_NAME, branchDTO.getName());
        assertEquals(SHA, branchDTO.getCommitSha());
    }
}