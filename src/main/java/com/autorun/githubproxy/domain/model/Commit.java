package com.autorun.githubproxy.domain.model;

import lombok.Getter;

@Getter
public class Commit {

    private String sha;

    public Commit() {
    }

    public Commit(String sha) {
        this.sha = sha;
    }
}
