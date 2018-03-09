package edu.gatech.a2340.shelterme.Model;

import android.os.Parcelable;

import java.io.Serializable;

public class Shelter implements Serializable {
    private String name;
    private String address;
    private String longitude;
    private String latitude;
    private String phone;
    private String capacity;
    private String restrictions;

    //Getters And Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPhone() {return phone;}
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCapacity() {return capacity;}
    public void setCapacity(String capacity) {this.capacity = capacity;}

    public String getRestrictions() {return restrictions;}
    public void setRestrictions(String restrictions) {this.restrictions = restrictions;}

    //Constructors
    public Shelter() {
        this("Default Shelter", "0", "all", "0.0", "0.0", "Default Address", "111-111-1111");
    }

    // DO NOT CHANGE THIS. Properly working ListView in MainActivity needs this. If you need to
    // change it talk to Michael Fan
    @Override
    public String toString() {
        return name;
    }


    public Shelter(String name, String capacity, String gender,
                   String longitude, String latitude, String address, String phone) {
        this.name = name;
        this.capacity = capacity;
        this.restrictions = gender;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phone = phone;
    }


}