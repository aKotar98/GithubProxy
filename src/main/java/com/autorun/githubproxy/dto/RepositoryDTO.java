package com.autorun.githubproxy.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RepositoryDTO {
    private String name;
    private String owner;
    private List<BranchDTO> branches;

    public RepositoryDTO() {
    }

    public RepositoryDTO(String name, String owner, List<BranchDTO> branches) {
        this.name = name;
        this.owner = owner;
        this.branches = branches;
    }
}
