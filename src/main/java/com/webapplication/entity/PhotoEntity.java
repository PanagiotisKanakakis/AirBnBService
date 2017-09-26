package com.webapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "Photos")
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PHOTO_ID")
    private Integer photoId;

    @Column(name = "PATH")
    private String path;

    @OneToOne
    @JsonIgnore
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "Residences_RESIDENCE_ID")
    @JsonIgnore
    private ResidenceEntity residenceEntity;

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ResidenceEntity getResidenceEntity() {
        return residenceEntity;
    }

    public void setResidenceEntity(ResidenceEntity residenceEntity) {
        this.residenceEntity = residenceEntity;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}