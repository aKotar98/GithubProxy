package com.autorun.githubproxy.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Builder
@Getter
@Setter
public class Repository {
    private Boolean fork;
    private String name;
    private Owner owner;
    private List<Branch> branches = Collections.emptyList();

    public Repository(Boolean fork, String name, Owner owner, List<Branch> branches) {
        this.fork = fork;
        this.name = name;
        this.owner = owner;
        this.branches = branches;
    }

    public Repository() {
    }
}
