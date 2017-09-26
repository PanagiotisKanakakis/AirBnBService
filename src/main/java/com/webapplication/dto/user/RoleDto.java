package com.webapplication.dto.user;

public enum RoleDto {

    HOST("HOST"),
    TENANT("TENANT");

    final private String description;

    private RoleDto(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
