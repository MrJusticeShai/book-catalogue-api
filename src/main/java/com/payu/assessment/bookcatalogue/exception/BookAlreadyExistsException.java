package com.payu.assessment.bookcatalogue.exception;

public class BookAlreadyExistsException extends RuntimeException{
    public BookAlreadyExistsException(String message) {
        super(message);
    }
}
