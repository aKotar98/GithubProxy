package com.autorun.githubproxy.domain.rest.client;

import com.autorun.githubproxy.domain.model.Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubClient {

    private RestTemplate restTemplate;

    public GithubClient() {
        restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> findAllRepositoriesByUserName(String username) {
        String gitHubResourceUrl = String.format("https://api.github.com/users/%s/repos", username);
        return restTemplate.getForEntity(gitHubResourceUrl, String.class);
    }


    public ResponseEntity<String> findAllBranchesForRepository(Repository repository) {
        String gitHubResourceUrl = String.format("https://api.github.com/repos/%s/%s/branches", repository.getOwner().getLogin(), repository.getName());
        return restTemplate.getForEntity(gitHubResourceUrl, String.class);
    }
}
