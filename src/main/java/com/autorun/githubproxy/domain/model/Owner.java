package com.autorun.githubproxy.domain.model;

import lombok.Getter;

@Getter
public class Owner {

    private String login;

    public Owner() {
    }

    public Owner(String login) {
        this.login = login;
    }
}
