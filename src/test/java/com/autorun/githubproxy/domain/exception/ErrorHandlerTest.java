package com.autorun.githubproxy.domain.exception;

import com.autorun.githubproxy.dto.ErrorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

class ErrorHandlerTest {
    private final ErrorHandler errorHandler = new ErrorHandler();

    @Test
    void shouldHandleNotFoundException() {
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.NOT_FOUND);

        ResponseEntity<ErrorDTO> responseEntity = errorHandler.handleError(httpClientErrorException);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Given github user not exist.", responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());

    }

    @Test
    void shouldHandleNotAcceptableException() {
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.NOT_ACCEPTABLE);

        ResponseEntity<ErrorDTO> responseEntity = errorHandler.handleError(httpClientErrorException);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
        assertEquals("Wrong content type. Only header type: application/json is allowed.", responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), responseEntity.getBody().getStatus());
    }

    @Test
    void shouldHandleOtherException() {
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed");

        ResponseEntity<ErrorDTO> responseEntity = errorHandler.handleError(httpClientErrorException);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, responseEntity.getStatusCode());
        assertEquals("405 Method not allowed", responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), responseEntity.getBody().getStatus());
    }
}