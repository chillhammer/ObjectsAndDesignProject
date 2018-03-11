package edu.gatech.a2340.shelterme.Model;

import android.os.Parcelable;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Shelter implements Serializable {
    private int id;
    private String name;
    private String address;
    private String longitude;
    private String latitude;
    private String phone;
    private int capacity;
    private int vacancies;
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

    public int getCapacity() {return capacity;}
    public void setCapacity(int capacity) {this.capacity = capacity;}

    public int getVacancies() {return vacancies;}
    void setVacancies(int vacancies) {
        this.vacancies = vacancies;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("shelters/" + id + "/vacancies").setValue(vacancies);
    }

    public String getRestrictions() {return restrictions;}
    public void setRestrictions(String restrictions) {this.restrictions = restrictions;}

    /**
     * Attempts to reserve <code>reservations</code> number of vacancies
     * @param reservations Number of vacancies to reserve
     * @return true if reservation successful, else false
     */
    boolean reserveVacancies(int reservations) {
        if (vacancies - reservations < 0)
            return false;
        setVacancies(vacancies - reservations);
        return true;
    }

    // DO NOT CHANGE THIS. Properly working ListView in MainActivity needs this. If you need to
    // change it talk to Michael Fan
    @Override
    public String toString() {
        return name;
    }


    public Shelter(int id, String name, int capacity, int vacancies, String gender,
                   String longitude, String latitude, String address, String phone) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.vacancies = vacancies;
        this.restrictions = gender;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phone = phone;
    }


}