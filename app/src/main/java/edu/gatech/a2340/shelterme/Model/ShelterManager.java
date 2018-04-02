package edu.gatech.a2340.shelterme.Model;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 3/7/18.
 */

final class ShelterManager {
    private static final ShelterManager ourInstance = new ShelterManager();

    static ShelterManager getInstance() {
        return ourInstance;
    }

    private List<Shelter> shelters;

    private ShelterManager() {
        shelters = new ArrayList<>();
    }

    /**
     * Updates the master shelter list with <code>snapshot</code>
     * @param snapshot The snapshot of the database
     */
    void updateShelterList(DataSnapshot snapshot) {
        List<Shelter> updatedShelters = new ArrayList<>();
        int id = 0;
        for (DataSnapshot child : snapshot.getChildren()) {
            String name = (String) child.child("name").getValue();
            assert child.child("capacity").getValue() != null;
            int capacity = ((Long) child.child("capacity").getValue()).intValue();
            assert child.child("vacancies").getValue() != null;
            int vacancies = ((Long) child.child("vacancies").getValue()).intValue();
            String gender = (String) child.child("restrictions").getValue();
            String longitude = (String) child.child("longitude").getValue();
            String lat = (String) child.child("latitude").getValue();
            String address = (String) child.child("address").getValue();
            String phone = (String) child.child("phone_number").getValue();
            updatedShelters.add(new Shelter(id, name, capacity, vacancies, gender, longitude,
                    lat, address, phone));
            id++;
        }
        shelters = updatedShelters;
    }

    /**
     * Attempts to reserve <code>reservations</code> number of vacancies
     * @param shelterId ID of shelter to reserve from
     *
     */
    void addReservations(int shelterId) {
        shelters.get(shelterId).addReservations(1);
    }

    /**
     * Check if reservations can be made
     * @param shelterId ID of shelter to reserve from
     * @param onFailure Callback depending on how check fails
     * @return true if valid reservation, else false
     */
    boolean validateReservations(int shelterId, IMessageable onFailure) {
        return shelters.get(shelterId).validateReservations(1, onFailure);
    }

    /**
     * Releases <code>releases</code> number of vacancies
     * @param shelterId ID of shelter to release from
     *
     */
    void releaseReservations(int shelterId) {
        shelters.get(shelterId).releaseReservations(1);
    }

    /**
     * Check if reservations can be made
     * @param shelterId ID of shelter to release from
     * @param onFailure Callback depending on how check fails
     * @return true if valid release, else false
     */
    boolean validateRelease(int shelterId, IMessageable onFailure) {
        return shelters.get(shelterId).validateRelease(1, onFailure);
    }

    List<Shelter> getShelterList() {
        return new ArrayList<>(shelters);
    }
}
