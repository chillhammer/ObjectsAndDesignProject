package edu.gatech.a2340.shelterme.Model;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Shelter implements Serializable {
    private final int id;
    private final String name;
    private final String address;
    private final String longitude;
    private final String latitude;
    private final String phone;
    private final int capacity;
    private int vacancies;
    private final String restrictions;

    //Getters
    /** Gets Id
     * @return id of shelter
     */
    public int getId() { return id; }

    /**
     *  getter for name
     * @return returns name of shelter
     */
    public String getName() {
        return name;
    }

    /**
     *  getter for address
     * @return returns address of shelter
     */
    public String getAddress() {
        return address;
    }

    /**
     *  getter for longitude
     * @return returns longitude of shelter
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *  getter for latitude
     * @return returns latitude of shelter
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *  getter for phone
     * @return returns phone number as string of shelter
     */
    public String getPhone() {return phone;}

    /**
     *  getter for capacity
     * @return returns capacity of shelter
     */
    public int getCapacity() {return capacity;}

    /**
     *  getter for vacancies
     * @return returns vacancies of shelter
     */
    public int getVacancies() {return vacancies;}

    /**
     *  getter for restrictions
     * @return returns restrictions of shelter
     */
    public String getRestrictions() {return restrictions;}

    /**
     * setter of vacancies for shelter
     * @param vacancies the new amount of vacancies
     */
    private void setVacancies(int vacancies) {
        this.vacancies = vacancies;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("shelters/" + id + "/vacancies").setValue(vacancies);
    }

    /**
     * Attempts to reserve <code>reservations</code> number of vacancies
     * @param reservations Number of vacancies to reserve
     */
    void addReservations(int reservations) {
        setVacancies(vacancies - reservations);
    }

    /**
     * Check if reservations can be made
     * @param reservations Number of reservations
     * @param onFailure Callback depending on how check fails
     * @return true if valid reservation, else false
     */
    boolean validateReservations(int reservations, IMessageable onFailure) {
        if (vacancies - reservations < 0) {
            onFailure.runWithMessage("Not enough vacancies to reserve");
            return false;
        }
        return true;
    }

    /**
     * Releases <code>releases</code> number of vacancies
     * @param releases Number of vacancies to release
     */
    void releaseReservations(int releases) {
        setVacancies(vacancies + releases);
    }

    /**
     * Check if reservations can be made
     * @param releases Number of releases to check
     * @param onFailure Callback depending on how check fails
     * @return true if valid release, else false
     */
    boolean validateRelease(int releases, IMessageable onFailure) {
        if (vacancies + releases > capacity) {
            onFailure.runWithMessage("Vacancies cannot exceed capacity");
            return false;
        }
        return true;
    }

    // DO NOT CHANGE THIS. Properly working ListView in MainActivity needs this. If you need to
    // change it talk to Michael Fan
    @Override
    public String toString() {
        return name;
    }


    Shelter(int id, String name, int capacity, int vacancies, String gender,
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