package com.marketplace.fsbz_marketplace.exceptions;

public class UserPasswordInvalidException extends CredentialsExceptions{

    private String message;

    public UserPasswordInvalidException(String message) {
        super(message);
        this.message=message;
    }

    public String getMessage() {

        return message;
    }


}
