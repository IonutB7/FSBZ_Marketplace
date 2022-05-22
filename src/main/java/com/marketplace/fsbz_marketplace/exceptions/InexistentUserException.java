package com.marketplace.fsbz_marketplace.exceptions;

public class InexistentUserException extends CredentialsExceptions{

    private String message;

    public InexistentUserException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
