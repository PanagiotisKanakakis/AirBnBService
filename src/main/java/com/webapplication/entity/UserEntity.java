package com.webapplication.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Users")
public class UserEntity {

    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "CITY")
    private String city;

    @Column(name = "SALT")
    private String salt;

    @OneToOne(mappedBy = "user", cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private PhotoEntity profilePhoto;

    @JsonIgnore
    @JoinTable(name = "Users_have_Roles", joinColumns = {
            @JoinColumn(name = "USERNAME")
    }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID")
    })
    @ManyToMany
    private List<RoleEntity> roles;

    @JoinTable(name = "Users_have_Residences", joinColumns = {
            @JoinColumn(name = "USERNAME")
    }, inverseJoinColumns = {
            @JoinColumn(name = "RESIDENCE_ID")
    })
    @JsonIgnore
    @ManyToMany
    private List<ResidenceEntity> residences = new ArrayList<>();

    @JoinTable(name = "User_has_SearchedLocations", joinColumns = {
            @JoinColumn(name = "USERNAME")
    }, inverseJoinColumns = {
            @JoinColumn(name = "SEARCH_ID")
    })
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    private List<SearchEntity> searchedLocations = new ArrayList<>();

    @JoinTable(name = "User_makes_Reservation", joinColumns = {
            @JoinColumn(name = "USERNAME")
    }, inverseJoinColumns = {
            @JoinColumn(name = "RESERVATION_ID")
    })
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    private List<ReservationEntity> reservedResidences = new ArrayList<>();

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private MailboxEntity mailbox = new MailboxEntity();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public List<ResidenceEntity> getResidences() {
        return residences;
    }

    public void setResidences(List<ResidenceEntity> residences) {
        this.residences = residences;
    }

    public List<SearchEntity> getSearchedLocations() {
        return searchedLocations;
    }

    public void setSearchedLocations(List<SearchEntity> searchedLocations) {
        this.searchedLocations = searchedLocations;
    }

    public List<ReservationEntity> getReservedResidences() {
        return reservedResidences;
    }

    public void setReservedResidences(List<ReservationEntity> reservedResidences) {
        this.reservedResidences = reservedResidences;
    }

    public MailboxEntity getMailbox() {
        return mailbox;
    }

    public void setMailbox(MailboxEntity mailbox) {
        this.mailbox = mailbox;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public PhotoEntity getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(PhotoEntity profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
