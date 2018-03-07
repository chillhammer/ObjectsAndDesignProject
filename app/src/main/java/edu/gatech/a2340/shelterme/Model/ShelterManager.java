package edu.gatech.a2340.shelterme.Model;

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
    }
}
