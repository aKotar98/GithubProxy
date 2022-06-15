package com.autorun.githubproxy.domain.exception;

import com.autorun.githubproxy.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

@Component
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> handleError(HttpClientErrorException e) {

        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .status(e.getStatusCode().value())
                    .message("Given github user not exist.")
                    .build();
            return new ResponseEntity<>(errorDTO, e.getStatusCode());
        }

        if (e.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .status(e.getStatusCode().value())
                    .message("Wrong content type. Only header type: application/json is allowed.")
                    .build();
            return new ResponseEntity<>(errorDTO, e.getStatusCode());
        }

        ErrorDTO error = ErrorDTO.builder()
                .status(e.getStatusCode().value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(error, e.getStatusCode());
    }


}

