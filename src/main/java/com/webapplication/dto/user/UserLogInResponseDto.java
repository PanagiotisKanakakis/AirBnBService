package com.webapplication.dto.user;


import java.util.List;
import java.util.UUID;

public class UserLogInResponseDto {

    private String username;

    private UUID authToken;

    private List<RoleDto> roleDtos;

    private String photo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getAuthToken() {
        return authToken;
    }

    public void setAuthToken(UUID authToken) {
        this.authToken = authToken;
    }

    public List<RoleDto> getRoleDtos() {
        return roleDtos;
    }

    public void setRoleDtos(List<RoleDto> roleDtos) {
        this.roleDtos = roleDtos;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
