package com.autorun.githubproxy.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public class MockJsonProcessingException extends JsonProcessingException {
    public MockJsonProcessingException(String message) {
        super(message);
    }
}
