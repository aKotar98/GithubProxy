package com.autorun.githubproxy.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GitHubProxyResponseDTO {
    private List<RepositoryDTO> repositories;
}
