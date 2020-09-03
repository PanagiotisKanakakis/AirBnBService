package com.main.dto.user;

import com.main.entity.CommentEntity;

import java.util.List;

public class UserProfileDto {

    private String name;

    private String surname;

    private String email;

    private String phoneNumber;

    private String city;

    private String photo;

    private List<CommentEntity> comments;

    private List<RoleDto> roleDtos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<RoleDto> getRoleDtos() {
        return roleDtos;
    }

    public void setRoleDtos(List<RoleDto> roleDtos) {
        this.roleDtos = roleDtos;
    }
}
