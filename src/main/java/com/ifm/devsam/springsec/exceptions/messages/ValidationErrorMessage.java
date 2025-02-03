package com.ifm.devsam.springsec.exceptions.messages;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.HashMap;

@Data
@NoArgsConstructor
public class ValidationErrorMessage {
    HttpStatus httpStatus;
    HashMap<String, String> validationErrors;
    private ZonedDateTime timestamp;
}