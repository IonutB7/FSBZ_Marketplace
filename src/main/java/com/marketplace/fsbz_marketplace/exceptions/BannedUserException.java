package com.marketplace.fsbz_marketplace.exceptions;

public class BannedUserException extends CredentialsExceptions{

    private String message;

    public BannedUserException(String message) {
        super(message);
        this.message=message;
    }

    public String getMessage() {

        return message;
    }


}
