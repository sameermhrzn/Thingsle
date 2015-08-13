package com.thingsle.sameer.thingsle.data;

/**
 * Created by Sameer on 7/13/2015.
 */
public class CitiesData {

    private Integer city_id;
    private String name;
    private double longi;
    private double lat;
    private String rating;
    private String thingsTO;
    private CountrieData countrieData;

    public CountrieData getCountrieData() {
        return countrieData;
    }

    public void setCountrieData(CountrieData countrieData) {
        this.countrieData = countrieData;
    }


    public CitiesData() {
    }


    public CitiesData(String name, double longi, double lat, String rating, String thingsTO) {
        this.name = name;
        this.longi = longi;
        this.lat = lat;
        this.rating = rating;
        this.thingsTO = thingsTO;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getThingsTO() {
        return thingsTO;
    }

    public void setThingsTO(String thingsTO) {
        this.thingsTO = thingsTO;
    }

   
}



