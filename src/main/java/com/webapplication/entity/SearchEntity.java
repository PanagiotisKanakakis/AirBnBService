package com.webapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by panagiotis on 30/7/2017.
 */
@Entity(name = "Search")
public class SearchEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SEARCH_ID")
    private Integer searchId;

    @Column(name = "LOCATION")
    private String location;

    @JsonIgnore
    @ManyToMany(mappedBy = "searchedLocations")
    private List<UserEntity> users = new ArrayList<>();

    public Integer getSearchId() {
        return searchId;
    }

    public void setSearchId(Integer searchId) {
        this.searchId = searchId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
