package com.autorun.githubproxy.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Branch {

    private String name;
    private Commit commit;

    public Branch(String name, Commit commit) {
        this.name = name;
        this.commit = commit;
    }

    public Branch() {
    }
}
