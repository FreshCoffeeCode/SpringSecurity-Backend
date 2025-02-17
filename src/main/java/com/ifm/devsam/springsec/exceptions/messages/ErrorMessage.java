package com.ifm.devsam.springsec.exceptions.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private HttpStatus httpStatus;
    private String message;
    private ZonedDateTime timestamp;
}