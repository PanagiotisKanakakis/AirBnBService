package com.webapplication.error;

/**
 * Created by panagiotis on 6/8/2017.
 */
public enum ResidenceError {

    RESIDENCE_ID_NOT_EXISTS("Residence not exists");

    private String description;

    private ResidenceError(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
