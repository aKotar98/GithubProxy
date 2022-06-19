package com.autorun.githubproxy.domain.service;

import com.autorun.githubproxy.domain.model.Branch;
import com.autorun.githubproxy.domain.model.Repository;
import com.autorun.githubproxy.domain.model.RepositoryWithBranches;
import com.autorun.githubproxy.domain.rest.client.GithubClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GitHubConsumerService {

    private GithubClient githubClient;

    @Autowired
    public GitHubConsumerService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    public List<RepositoryWithBranches> getNotForkRepositoriesByUserName(String username) throws JsonProcessingException {
        List<Repository> repositories = githubClient.findAllRepositoriesByUserName(username);
        if(!repositories.isEmpty()) {
            List<Repository> notForkRepositories = findNotForkRepositories(repositories);
            return createRepositoriesWithBranches(notForkRepositories);
        }
        return Collections.emptyList();
    }

    private List<Repository> findNotForkRepositories(List<Repository> repositories) {
        return repositories.stream().filter(a -> !a.getFork())
                .collect(Collectors.toList());
    }

    private List<RepositoryWithBranches> createRepositoriesWithBranches(List<Repository> repositories) {
        return repositories.parallelStream()
                .map(x -> {
                    try {
                        return addBranchesInformationToRepository(x);
                    } catch (JsonProcessingException e) {
                        throw new IllegalStateException(e);
                    }
                }).collect(Collectors.toList());
    }

    private RepositoryWithBranches addBranchesInformationToRepository(Repository repository) throws JsonProcessingException {
        List<Branch> branches = githubClient.findAllBranchesForRepository(repository);
        return RepositoryWithBranches.builder()
                .repository(repository)
                .branches(branches)
                .build();
    }
}
