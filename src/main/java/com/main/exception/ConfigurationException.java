package com.main.exception;


import com.main.error.UserError;

public class ConfigurationException extends RestException {

    public ConfigurationException(UserError userError) {
        super(userError.getDescription());
    }
}
