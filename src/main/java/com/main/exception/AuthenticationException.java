package com.main.exception;


import com.main.error.UserError;

public class AuthenticationException extends RestException {

    public AuthenticationException(UserError userError) {
        super(userError.getDescription());
    }
}
