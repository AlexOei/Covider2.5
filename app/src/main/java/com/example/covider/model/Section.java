package com.example.covider.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Section implements Serializable {
    private String sectionNum;
    private String buildingCode;
    private ArrayList<User> users;

    public Section() {

    }

    public Section(String sectionNum, String buildingCode, ArrayList<User> users) {
        this.sectionNum = sectionNum;
        this.buildingCode = buildingCode;
        this.users = users;
    }

    public String getSectionNum() {
        return sectionNum;
    }

    public String getBuilding() {
        return buildingCode;
    }

    public ArrayList<User> getUser() {
        return users;
    }

    public void setSectionNum(String sectionNum) {
        this.sectionNum = sectionNum;
    }

    public void setBuilding(String building) {
        this.buildingCode = building;
    }

    public void setUser(ArrayList<User> users) {
        this.users = users;
    }
}

