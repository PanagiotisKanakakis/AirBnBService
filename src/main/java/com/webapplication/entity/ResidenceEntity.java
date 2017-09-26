package com.webapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Residences")
public class ResidenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESIDENCE_ID")
    private Integer residenceId;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "GEO_X")
    private Double geoX;

    @Column(name = "GEO_Y")
    private Double geoY;

    @Column(name = "CAPACITY")
    private Integer capacity;

    @Column(name = "PRIZE")
    private Integer prize;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "RULES")
    private String rules;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "BATHROOMS")
    private Integer bathrooms;

    @Column(name = "SIZE")
    private Double size;

    @Column(name = "BEDROOMS")
    private Integer bedrooms;

    @Column(name = "LIVING_ROOM")
    private Boolean livingRoom;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "BEDS")
    private Integer beds;

    @ElementCollection
    @Column(name = "Photo paths")
    private List<String> photoPaths = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "residences")
    private List<UserEntity> users;

    @OneToMany(mappedBy = "residenceEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "residenceEntity")
    @JsonIgnore
    private List<ReservationEntity> reservationInfo = new ArrayList<>();

    public Integer getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Integer residenceId) {
        this.residenceId = residenceId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getGeoX() {
        return geoX;
    }

    public void setGeoX(Double geoX) {
        this.geoX = geoX;
    }

    public Double getGeoY() {
        return geoY;
    }

    public void setGeoY(Double geoY) {
        this.geoY = geoY;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getPrize() {
        return prize;
    }

    public void setPrize(Integer prize) {
        this.prize = prize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Boolean getLivingRoom() {
        return livingRoom;
    }

    public void setLivingRoom(Boolean livingRoom) {
        this.livingRoom = livingRoom;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getPhotoPaths() {
        return photoPaths;
    }

    public void setPhotoPaths(List<String> photoPaths) {
        this.photoPaths = photoPaths;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public List<ReservationEntity> getReservationInfo() {
        return reservationInfo;
    }

    public void setReservationInfo(List<ReservationEntity> reservationInfo) {
        this.reservationInfo = reservationInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }
}
