package com.example.covider.model;

public class Building {


    public Building(String latitude, String longitude, String frequent, String code, String name){
        this.latitude = latitude;
        this.longitude = longitude;
        this.code = code;
        this.name = name;
    }

    public Building() {

    }
    private String latitude = null;
    private String longitude = null;
    private String code = null;
    private String name = null;
    private Integer risk = 0;



    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String Latitude) {
        latitude = Latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String Longitude) {
        longitude = Longitude;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRisk() {
        return risk;
    }

    public void setRisk(Integer risk) {
        this.risk = risk;
    }
}
