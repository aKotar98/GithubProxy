package com.autorun.githubproxy.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RepositoryWithBranches {
    private Repository repository;
    private List<Branch> branches;
}
