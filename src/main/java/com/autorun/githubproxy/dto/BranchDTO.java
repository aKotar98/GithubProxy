package com.autorun.githubproxy.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BranchDTO {
    private String name;
    private String commitSha;
}
