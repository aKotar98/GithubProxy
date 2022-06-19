package com.autorun.githubproxy.domain.controller;

import com.autorun.githubproxy.domain.model.Branch;
import com.autorun.githubproxy.domain.model.RepositoryWithBranches;
import com.autorun.githubproxy.dto.BranchDTO;
import com.autorun.githubproxy.dto.GitHubProxyResponseDTO;
import com.autorun.githubproxy.dto.RepositoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseMapper {
    public GitHubProxyResponseDTO mapToResponseDto(List<RepositoryWithBranches> repositories) {
        List<RepositoryDTO> repositoryDTOs = repositories.stream()
                .map(this::toRepositoryDTO)
                .collect(Collectors.toList());
        return GitHubProxyResponseDTO.builder()
                .repositories(repositoryDTOs)
                .build();
    }

    private RepositoryDTO toRepositoryDTO(RepositoryWithBranches repository) {
        List<BranchDTO> branchDTOs = toBranchDTOs(repository);
        return RepositoryDTO.builder()
                .name(repository.getRepository().getName())
                .owner(repository.getRepository().getOwner().getLogin())
                .branches(branchDTOs)
                .build();
    }

    private List<BranchDTO> toBranchDTOs(RepositoryWithBranches repository) {
        return repository.getBranches().stream()
                .map(this::toBranch)
                .collect(Collectors.toList());
    }

    private BranchDTO toBranch(Branch branch) {
        return BranchDTO.builder()
                .name(branch.getName())
                .commitSha(branch.getCommit().getSha())
                .build();
    }
}
