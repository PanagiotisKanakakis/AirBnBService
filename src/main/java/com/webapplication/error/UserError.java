package com.webapplication.error;


public enum UserError {

    USERNAME_ALREADY_EXISTS("Username already exists"),
    INVALID_CREDENTIALS("Username and password do not match."),
    CONFIGURATION_ERROR("Configuration error"),
    USER_NOT_EXISTS("User not exists"),
    USER_EMAIL_ALREADY_EXISTS("Email already exists");

    private String description;

    private UserError(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
