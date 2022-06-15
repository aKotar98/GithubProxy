package com.autorun.githubproxy.domain.controller;


import com.autorun.githubproxy.domain.service.GitHubConsumerService;
import com.autorun.githubproxy.domain.model.Repository;
import com.autorun.githubproxy.dto.GitHubProxyResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GithubProxyController.RESOURCE_PATH)
public class GithubProxyController {

    static final String RESOURCE_PATH = "/github/proxy";

    private final GitHubConsumerService gitHubConsumerService;
    private final ResponseMapper responseMapper;

    @Autowired
    public GithubProxyController(GitHubConsumerService gitHubConsumerService, ResponseMapper responseMapper) {
        this.gitHubConsumerService = gitHubConsumerService;
        this.responseMapper = responseMapper;
    }

    @GetMapping(path = "/repository/{username}")
    @Operation(summary = "Find repositories for user by username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = GitHubProxyResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found.", content = @Content),
            @ApiResponse(responseCode = "406", description = "Wrong content type.", content = @Content)})
    ResponseEntity<GitHubProxyResponseDTO> getRepositoryNameByUsername(@PathVariable String username) throws JsonProcessingException {
        List<Repository> repositories = gitHubConsumerService.getNotForkRepositoriesByUserName(username);
        GitHubProxyResponseDTO responseDTO = responseMapper.mapToResponseDto(repositories);
        return ResponseEntity.ok(responseDTO);
    }

}
