package edu.gatech.a2340.shelterme.Model;

import com.google.firebase.database.DataSnapshot;

import java.util.List;

/**
 * Created by Brandon on 3/7/18.
 */

public class ManagerFacade {
    private static final ManagerFacade ourInstance = new ManagerFacade();

    public static ManagerFacade getInstance() {
        return ourInstance;
    }

    final UserManager userManager = UserManager.getInstance();
    final ShelterManager shelterManager = ShelterManager.getInstance();

    private ManagerFacade() {

    }

    //Public methods go here
    public void updateShelterList(DataSnapshot snapshot) {
        shelterManager.updateShelterList(snapshot);
    }

    /**
     * Attempts to reserve vacancies at the specified shelter
     * @param shelterId Id of shelter to make reservations at
     * @param reservations Number of reservations
     * @return true if reservation successful, else false
     */
    boolean reserveVacancies(int shelterId, int reservations) {
        return shelterManager.reserveVacancies(shelterId, reservations);
    }

    public List<Shelter> getShelterList() {
        return shelterManager.getShelterList();
    }

    public void attemptSignIn(String email, String password,
                              IMessageable onSuccess, IMessageable onFailure) {
        userManager.attemptFirebaseLogin(email, password, onSuccess, onFailure);
    }

    public void attemptRegister(String email, String password, String confirmPassword,
                                UserType userType, IMessageable onSuccess, IMessageable onFailure) {
        userManager.attemptFirebaseRegistration(email, password, confirmPassword, userType,
                onSuccess, onFailure);
    }

    public void signOut() {
        userManager.signOut();
    }
}
