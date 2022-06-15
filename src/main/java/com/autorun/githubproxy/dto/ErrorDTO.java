package com.autorun.githubproxy.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorDTO {
    private Integer status;
    private String message;
}
