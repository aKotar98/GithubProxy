package com.autorun.githubproxy.domain.controller;

import com.autorun.githubproxy.domain.model.Branch;
import com.autorun.githubproxy.domain.model.Commit;
import com.autorun.githubproxy.domain.model.Owner;
import com.autorun.githubproxy.domain.model.Repository;
import org.assertj.core.util.Lists;

import java.util.Collections;
import java.util.List;

public class TestObjectFactory {

    public static final String LOGIN = "userXyz";
    public static final String SHA = "0e269794eb7d11d6f492e3eecefc0fc8173aee5a";
    public static final String BRANCH_NAME = "master";
    public static final String REPOSITORY_NAME = "ArcGame";

    public List<Repository> createRepositories() {
        Branch branch = Branch.builder()
                .name(BRANCH_NAME)
                .commit(new Commit(SHA))
                .build();
        Repository repository = Repository.builder()
                .name(REPOSITORY_NAME)
                .owner(new Owner(LOGIN))
                .fork(false)
                .branches(Collections.singletonList(branch))
                .build();
        return Lists.newArrayList(repository);
    }
}
