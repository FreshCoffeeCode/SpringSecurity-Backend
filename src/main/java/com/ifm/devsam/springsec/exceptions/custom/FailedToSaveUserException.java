package com.ifm.devsam.springsec.exceptions.custom;

public class FailedToSaveUserException extends RuntimeException {

    public FailedToSaveUserException() {
        super();
    }

    public FailedToSaveUserException(String message) {
        super(message);
    }
}
