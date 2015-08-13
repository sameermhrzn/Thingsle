package com.thingsle.sameer.thingsle.data;

/**
 * Created by Sameer on 8/11/2015.
 */
public class CountrieData {

    private long id;
    private String name;
    private double lat;
    private double longi;
    private CitiesData citiesData;

    public CountrieData(String name, double lat, double longi) {
        this.name = name;
        this.lat = lat;
        this.longi = longi;
    }

    public CountrieData() {

    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public CitiesData getCitiesData() {
        return citiesData;
    }

    public void setCitiesData(CitiesData citiesData) {
        this.citiesData = citiesData;
    }
}