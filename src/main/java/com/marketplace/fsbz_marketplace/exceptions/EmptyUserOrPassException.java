package com.marketplace.fsbz_marketplace.exceptions;

public class EmptyUserOrPassException extends  CredentialsExceptions{

    private String message;

    public EmptyUserOrPassException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;

    }
}
