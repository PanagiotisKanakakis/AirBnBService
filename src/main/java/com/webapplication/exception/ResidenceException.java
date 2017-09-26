package com.webapplication.exception;

import com.webapplication.error.ResidenceError;

/**
 * Created by panagiotis on 6/8/2017.
 */
public class ResidenceException extends RestException{

    public ResidenceException(ResidenceError residenceError) {
        super(residenceError.getDescription());
    }

}
