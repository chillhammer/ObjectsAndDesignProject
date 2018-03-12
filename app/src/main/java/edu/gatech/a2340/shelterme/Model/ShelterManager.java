package edu.gatech.a2340.shelterme.Model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 3/7/18.
 */

public class ShelterManager {
    private static final ShelterManager ourInstance = new ShelterManager();

    static ShelterManager getInstance() {
        return ourInstance;
    }

    private List<Shelter> shelters;

    private ShelterManager() {
        shelters = new ArrayList<>();
    }

    void updateShelterList(DataSnapshot snapshot) {
        List<Shelter> updatedShelters = new ArrayList<>();
        int id = 0;
        for (DataSnapshot child : snapshot.getChildren()) {
            String name = (String) child.child("name").getValue();
            int capacity = ((Long) child.child("capacity").getValue()).intValue();
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
     * Attempts to reserve vacancies at the specified shelter
     * @param shelterId Id of shelter to make reservations at
     * @param reservations Number of reservations
     * @return true if reservation successful, else false
     */
    boolean reserveVacancies(int shelterId, int reservations) {
        return shelters.get(shelterId).reserveVacancies(reservations);
    }

    List<Shelter> getShelterList() {
        return new ArrayList<>(shelters);
    }
}
