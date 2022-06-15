package com.autorun.githubproxy.domain.controller;

import com.autorun.githubproxy.domain.model.Branch;
import com.autorun.githubproxy.domain.model.Commit;
import com.autorun.githubproxy.domain.model.Owner;
import com.autorun.githubproxy.domain.model.Repository;
import com.autorun.githubproxy.dto.BranchDTO;
import com.autorun.githubproxy.dto.GitHubProxyResponseDTO;
import com.autorun.githubproxy.dto.RepositoryDTO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.autorun.githubproxy.domain.controller.TestObjectFactory.*;
import static org.junit.jupiter.api.Assertions.*;

class ResponseMapperTest {

    private final ResponseMapper responseMapper = new ResponseMapper();
    private final TestObjectFactory testObjectFactory = new TestObjectFactory();

    @Test
    void shouldMapToResponseDto() {
        List<Repository> repositories = testObjectFactory.createRepositories();

        GitHubProxyResponseDTO responseDTO = responseMapper.mapToResponseDto(repositories);

        assertEquals(1, responseDTO.getRepositories().size());
        RepositoryDTO repositoryDTO = responseDTO.getRepositories().iterator().next();
        assertEquals(REPOSITORY_NAME, repositoryDTO.getName());
        assertEquals(LOGIN, repositoryDTO.getOwner());
        assertEquals(1, repositoryDTO.getBranches().size());
        BranchDTO branchDTO = repositoryDTO.getBranches().iterator().next();
        assertEquals(BRANCH_NAME, branchDTO.getName());
        assertEquals(SHA, branchDTO.getCommitSha());
    }


}