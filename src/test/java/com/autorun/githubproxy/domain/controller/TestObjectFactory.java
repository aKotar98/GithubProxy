package com.autorun.githubproxy.domain.controller;

import com.autorun.githubproxy.domain.model.*;
import org.assertj.core.util.Lists;

import java.util.Collections;
import java.util.List;

public class TestObjectFactory {

    public static final String SHA = "0e269794eb7d11d6f492e3eecefc0fc8173aee5a";
    public static final String BRANCH_NAME = "master";
    public static final String REPOSITORY_NAME = "ArcGame";
    public static final String USER_NAME = "user";


    public List<RepositoryWithBranches> createRepositoriesWithBranches() {
        Branch branch = Branch.builder()
                .name(BRANCH_NAME)
                .commit(new Commit(SHA))
                .build();
        Repository repository = createRepository();
        RepositoryWithBranches repositoryWithBranches = RepositoryWithBranches.builder()
                .repository(repository)
                .branches(Collections.singletonList(branch))
                .build();
        return Lists.newArrayList(repositoryWithBranches);
    }

    public List<Repository> getForkRepository() {
        List<Repository> repositories = createRepositories();
        Repository repository = repositories.iterator().next();
        repository.setFork(true);
        return repositories;
    }

    public List<Repository> createRepositories() {
        Repository repository = createRepository();
        return Lists.newArrayList(repository);
    }

    private Repository createRepository() {
        return Repository.builder()
                .name(REPOSITORY_NAME)
                .owner(new Owner(USER_NAME))
                .fork(false)
                .build();
    }
}
