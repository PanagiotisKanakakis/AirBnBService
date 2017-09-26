package com.webapplication.exception;


import com.webapplication.error.UserError;

public class ConfigurationException extends RestException {

    public ConfigurationException(UserError userError) {
        super(userError.getDescription());
    }
}
