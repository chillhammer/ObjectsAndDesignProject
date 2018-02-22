package edu.gatech.a2340.shelterme.Model;

public class Shelter {
    private String name;
    private String address;
    private double longitude;
    private double latitude;
    private String phone;

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

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    //Constructors
    public Shelter() {
        this.name = "Default Shelter";
        this.address = "Default Address";
        this.longitude = 100;
        this.latitude = 100;
        this.phone = "404-300-1202";
    }

    public Shelter(String name, String address,
                   double longitude, double latitude, String phone) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phone = phone;
    }
}