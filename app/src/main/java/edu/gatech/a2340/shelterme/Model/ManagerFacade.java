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

    /**
     * Updates the master shelter list with <code>snapshot</code>
     * @param snapshot The snapshot of the database
     */
    public void updateShelterList(DataSnapshot snapshot) {
        shelterManager.updateShelterList(snapshot);
    }

    /**
     * Attempts to reserve vacancies at the specified shelter
     * @param shelterId Id of shelter to make reservations at
     * @param reservedVacancies Number of reservations
     * @param onFailure Callback for reservation failure
     * @return true if reservation successful, else false
     */
    public boolean addReservations(int shelterId, int reservedVacancies, IMessageable onFailure) {
        if (!(userManager.validateReservations(shelterId, reservedVacancies, onFailure)
                && shelterManager.validateReservations(shelterId, reservedVacancies, onFailure)))
            return false;
        userManager.addReservations(shelterId, reservedVacancies);
        shelterManager.addReservations(shelterId, reservedVacancies);
        return true;
    }

    /**
     * Releases a number of vacancies at specified shelter
     * @param shelterId Id of shelter of release vacancies from
     * @param releasedVacancies Number of vacancies to release
     * @param onFailure Callback for release failure
     * @return true if release successful, else false
     */
    public boolean releaseReservations(int shelterId, int releasedVacancies, IMessageable onFailure) {
        if (!(userManager.validateRelease(shelterId, releasedVacancies, onFailure)
                && shelterManager.validateRelease(shelterId, releasedVacancies, onFailure)))
            return false;
        userManager.releaseReservations(shelterId, releasedVacancies);
        shelterManager.releaseReservations(shelterId, releasedVacancies);
        return true;
    }

    /**
     * Gets the number of reservations by current user at specified shelter
     * @param shelterId ShelterID of shelter to check reservations
     * @return Number of reservations held at specified shelter
     */
    public int getReservations(int shelterId) {
        return userManager.getReservations(shelterId);
    }

    public List<Shelter> getShelterList() {
        return shelterManager.getShelterList();
    }

    /**
     * Attempts to log in through firebase authentication
     * and grab user information
     * @param email User email
     * @param password User password
     * @param onSuccess Callback for successful login
     * @param onFailure Callback for unsuccessful login
     */
    public void attemptSignIn(String email, String password,
                              IMessageable onSuccess, IMessageable onFailure) {
        userManager.attemptFirebaseLogin(email, password, onSuccess, onFailure);
    }

    /**
     * Attempts to register a new user with the passed credentials with firebase
     * @param email New user email
     * @param password New user password
     * @param confirmPassword Confirm password
     * @param userType New user's UserType
     * @param onSuccess Callback for successful registration
     * @param onFailure Callback for unsuccessful registration
     */
    public void attemptRegister(String email, String password, String confirmPassword,
                                UserType userType, IMessageable onSuccess, IMessageable onFailure) {
        userManager.attemptFirebaseRegistration(email, password, confirmPassword, userType,
                onSuccess, onFailure);
    }

    public void signOut() {
        userManager.signOut();
    }
}
