package com.autorun.githubproxy.domain.service;

import com.autorun.githubproxy.domain.model.Branch;
import com.autorun.githubproxy.domain.model.Repository;
import com.autorun.githubproxy.domain.rest.client.GithubClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GitHubConsumerService {

    private ObjectMapper objectMapper;
    private GithubClient githubClient;

    public GitHubConsumerService(GithubClient githubClient) {
        this.githubClient = githubClient;
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<Repository> getNotForkRepositoriesByUserName(String username) throws JsonProcessingException {
        ResponseEntity<String> response = githubClient.findAllRepositoriesByUserName(username);
        List<Repository> repositories = readDataFromRepositoriesResponse(response);
        List<Repository> notForkRepositories = findNotForkRepositories(repositories);
        addBranchesInformationToRepositories(notForkRepositories);
        return notForkRepositories;
    }


    private List<Repository> readDataFromRepositoriesResponse(ResponseEntity<String> response) throws JsonProcessingException {
        if (Objects.nonNull(response) && response.getStatusCode().value() == 200) {
            String jsonInput = response.getBody();
            return objectMapper.readValue(jsonInput, new TypeReference<List<Repository>>() {
            });
        }
        return Collections.emptyList();
    }

    private List<Repository> findNotForkRepositories(List<Repository> repositories) {
        return repositories.stream().filter(a -> !a.getFork())
                .collect(Collectors.toList());
    }

    private void addBranchesInformationToRepositories(List<Repository> repositories) {
        repositories.parallelStream()
                .forEach(x -> {
                    try {
                        addBranchesInformationToRepository(x);
                    } catch (JsonProcessingException e) {
                        throw new IllegalStateException(e);
                    }
                });
    }

    private void addBranchesInformationToRepository(Repository repository) throws JsonProcessingException {
        ResponseEntity<String> response = githubClient.findAllBranchesForRepository(repository);
        List<Branch> branches = readDataFromBranchesResponse(response);
        repository.setBranches(branches);
    }

    private List<Branch> readDataFromBranchesResponse(ResponseEntity<String> response) throws JsonProcessingException {
        if (Objects.nonNull(response) && response.getStatusCode().value() == 200) {
            String jsonInput = response.getBody();
            return objectMapper.readValue(jsonInput, new TypeReference<List<Branch>>() {
            });
        }
        return Collections.emptyList();
    }
}
