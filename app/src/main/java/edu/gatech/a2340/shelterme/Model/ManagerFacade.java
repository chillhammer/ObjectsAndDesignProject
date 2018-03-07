package edu.gatech.a2340.shelterme.Model;

/**
 * Created by Brandon on 3/7/18.
 */

public class ManagerFacade {
    private static final ManagerFacade ourInstance = new ManagerFacade();

    public static ManagerFacade getInstance() {
        return ourInstance;
    }

    final UserManager userManager = UserManager.getInstance();

    private ManagerFacade() {

    }

    //Public methods go here
    public void attemptSignIn(String email, String password,
                              IMessageable onSuccess, IMessageable onFailure) {
        userManager.attemptFirebaseLogin(email, password, onSuccess, onFailure);
    }

    public void signOut() {
        userManager.signOut();
    }
}
