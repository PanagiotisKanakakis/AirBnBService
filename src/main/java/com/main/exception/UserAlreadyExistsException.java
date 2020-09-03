package com.main.exception;


import com.main.error.UserError;

public class UserAlreadyExistsException extends RestException {

    public UserAlreadyExistsException(UserError userError) {
        super(userError.getDescription());
    }
}
