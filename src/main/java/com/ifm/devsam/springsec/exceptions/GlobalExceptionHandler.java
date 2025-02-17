package com.ifm.devsam.springsec.exceptions;

import com.ifm.devsam.springsec.exceptions.custom.UserAlreadyExistsException;
import com.ifm.devsam.springsec.exceptions.custom.FailedToSaveUserException;
import com.ifm.devsam.springsec.exceptions.custom.UserNotFoundException;
import com.ifm.devsam.springsec.exceptions.messages.ErrorMessage;
import com.ifm.devsam.springsec.exceptions.messages.ValidationErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorMessage handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ValidationErrorMessage validationErrorMessage = new ValidationErrorMessage();
        HashMap<String, String> errorMap = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    errorMap.put(error.getField(), error.getDefaultMessage());
                });

        validationErrorMessage.setHttpStatus(HttpStatus.BAD_REQUEST);
        validationErrorMessage.setValidationErrors(errorMap);
        validationErrorMessage.setTimestamp(ZonedDateTime.now(ZoneId.of("Z")));

        return validationErrorMessage;
    }

    @ExceptionHandler(FailedToSaveUserException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleFailedToSaveCustomerException(FailedToSaveUserException ex) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z")));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleCustomerAlreadyExistsException(UserAlreadyExistsException ex) {
        return new ErrorMessage(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z")));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleUserNotFoundException(UserNotFoundException ex) {
        return new ErrorMessage(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z")));
    }

}
