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

    //Getters
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getPhone() {return phone;}

    public int getCapacity() {return capacity;}

    public int getVacancies() {return vacancies;}

    public String getRestrictions() {return restrictions;}

    private void setVacancies(int vacancies) {
        this.vacancies = vacancies;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("shelters/" + id + "/vacancies").setValue(vacancies);
    }

    /**
     * Attempts to reserve <code>reservations</code> number of vacancies
     * @param reservations Number of vacancies to reserve
     * @param onFailure Callback if reservation fails
     * @return true if reservation successful, else false
     */
    boolean addReservations(int reservations, IMessageable onFailure) {
        if (vacancies - reservations < 0) {
            onFailure.runWithMessage("Not enough vacancies to reserve");
            return false;
        }
        setVacancies(vacancies - reservations);
        return true;
    }

    /**
     * Releases <code>releases</code> number of vacancies
     * @param releases Number of vacancies to release
     */
    void releaseReservations(int releases) {
        setVacancies(vacancies + releases <= capacity ? vacancies + releases : capacity);
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