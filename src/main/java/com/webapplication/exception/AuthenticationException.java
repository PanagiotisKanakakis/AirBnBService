package com.webapplication.exception;


import com.webapplication.error.UserError;

public class AuthenticationException extends RestException {

    public AuthenticationException(UserError userError) {
        super(userError.getDescription());
    }
}
