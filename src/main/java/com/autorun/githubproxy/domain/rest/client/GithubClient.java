package com.autorun.githubproxy.domain.rest.client;

import com.autorun.githubproxy.domain.model.Branch;
import com.autorun.githubproxy.domain.model.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class GithubClient {

    @Value("${base.url.github}")
    private String baseUrl;

    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    public void setObjectMapper(@Lazy ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setRestTemplate(@Lazy RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public GithubClient() {
    }

    public List<Repository> findAllRepositoriesByUserName(String username) throws JsonProcessingException {
        String gitHubResourceUrl = String.format("%s/users/%s/repos",baseUrl, username);
        ResponseEntity<String> response = restTemplate.getForEntity(gitHubResourceUrl, String.class);
        return  readDataFromRepositoriesResponse(response);
    }

    private List<Repository> readDataFromRepositoriesResponse(ResponseEntity<String> response) throws JsonProcessingException {
        if (Objects.nonNull(response) && response.getStatusCode().value() == 200) {
            String jsonInput = response.getBody();
            return objectMapper.readValue(jsonInput, new TypeReference<List<Repository>>() {
            });
        }
        return Collections.emptyList();
    }


    public List<Branch> findAllBranchesForRepository(Repository repository) throws JsonProcessingException {
        String gitHubResourceUrl = String.format("%s/repos/%s/%s/branches", baseUrl,repository.getOwner().getLogin(), repository.getName());
        ResponseEntity<String> response = restTemplate.getForEntity(gitHubResourceUrl, String.class);
        return readDataFromBranchesResponse(response);
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
