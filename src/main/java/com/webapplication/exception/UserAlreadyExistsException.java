package com.webapplication.exception;


import com.webapplication.error.UserError;

public class UserAlreadyExistsException extends RestException {

    public UserAlreadyExistsException(UserError userError) {
        super(userError.getDescription());
    }
}
