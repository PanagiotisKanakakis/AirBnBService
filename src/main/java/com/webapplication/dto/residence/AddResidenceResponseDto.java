package com.webapplication.dto.residence;


import java.util.List;

public class AddResidenceResponseDto {

    private Integer residenceId;

    private String address;

    private Double geoX;

    private Double geoY;

    private String datesReserved;

    private Integer capacity;

    private Integer prize;

    private String type;

    private String rules;

    private String description;

    private Integer bathrooms;

    private Double size;

    private Integer bedrooms;

    private Boolean livingRoom;

    private String location;

    private String  username;

    private List<String> photoPaths;

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

    public String getDatesReserved() {
        return datesReserved;
    }

    public void setDatesReserved(String datesReserved) {
        this.datesReserved = datesReserved;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getPhotoPaths() {
        return photoPaths;
    }

    public void setPhotoPaths(List<String> photoPaths) {
        this.photoPaths = photoPaths;
    }

}
