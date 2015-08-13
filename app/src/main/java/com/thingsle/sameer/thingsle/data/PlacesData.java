package com.thingsle.sameer.thingsle.data;

/**
 * Created by Sameer on 8/11/2015.
 */
public class PlacesData {
    private int place_id ;
    private String name;
    private double longi;
    private double lat;
    private String rating;
    private String description;
    private String details;
    private Boolean status;
    private CitiesData citiesData;

    public PlacesData() {

    }

    public PlacesData(String name, double longi, double lat, String rating, String description, String details, Boolean status) {
        this.name = name;
        this.longi = longi;
        this.lat = lat;
        this.rating = rating;
        this.description = description;
        this.details = details;
        this.status = status;
    }

    public Integer getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Integer place_id) {
        this.place_id = place_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public CitiesData getCitiesData() {
        return citiesData;
    }

    public void setCitiesData(CitiesData citiesData) {
        this.citiesData = citiesData;
    }
}
