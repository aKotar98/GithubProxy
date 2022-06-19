package com.autorun.githubproxy.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Repository {
    private Boolean fork;
    private String name;
    private Owner owner;

    public Repository(Boolean fork, String name, Owner owner) {
        this.fork = fork;
        this.name = name;
        this.owner = owner;
    }

    public Repository() {
    }
}
