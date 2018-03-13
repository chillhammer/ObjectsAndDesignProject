package edu.gatech.a2340.shelterme.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserManager {
    private static final UserManager ourInstance = new UserManager();

    static UserManager getInstance() {
        return ourInstance;
    }

    private UserManager(){}

    private User user;

    /**
     * Validates login email and password with hardcoded rules
     * @param email Email to validate
     * @param password Password to validate
     * @return null if valid, else a string containing the input discrepancy
     */
    private String validateLoginInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return "Email or password cannot be empty";
        }
        return null;
    }

    /**
     * Validates registration email and password with hardcoded rules
     * @param email Email to validate
     * @param password Password to validate
     * @param confirmPassword Confirm Password to validate
     * @return null if valid, else a string containing the input discrepancy
     */
    private String validateRegistrationInput(String email, String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            if (email.isEmpty() || password.isEmpty()) {
                return "All fields must be filled";
            } else {
                if (password.length() < 8) {
                    return "Password must be at least 8 characters";
                }
                return null;
            }
        } else { // password and confirm password don't match
            return "Passwords must match";
        }
    }

    /**
     * Attempts to log in through firebase authentication
     * and grab user information
     * @param email User email
     * @param password User password
     * @param onSuccess Callback for successful login
     * @param onFailure Callback for unsuccessful login
     */
    void attemptFirebaseLogin(String email, String password, final IMessageable onSuccess, final IMessageable onFailure) {
        String message = validateLoginInput(email, password);
        if (message != null) {
            onFailure.runWithMessage(message);
            return;
        }
        user = new User(email, password, onSuccess, onFailure);
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
    void attemptFirebaseRegistration(final String email, final String password,
                                     String confirmPassword,
                                     final UserType userType,
                                     final IMessageable onSuccess,
                                     final IMessageable onFailure) {
        String message = validateRegistrationInput(email, password, confirmPassword);
        if (message != null) {
            onFailure.runWithMessage(message);
            return;
        }
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            auth.signOut();
                            auth.signInWithEmailAndPassword(email, password)
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            //Registration successful, setup user information in database
                                            Log.w("UserManager", "createUserWithEmail:Registration successful. Attempting to add user data to database");
                                            FirebaseUser user = auth.getCurrentUser();
                                            DatabaseReference ref = database.getReference().child("users").child(user.getUid());
                                            ref.child("email").setValue(email);
                                            ref.child("userType").setValue(userType.name());
                                            ref.child("reservations/shelterId").setValue(-1);
                                            ref.child("reservations/reservedVacancies").setValue(0);
                                            Log.w("UserManager", "createUserWithEmail:Data upload attempt finished.");
                                            onSuccess.runWithMessage("Registration successful, you may now sign in!");
                                        }
                                    });

                        } else {
                            //Registration failed
                            Log.w("UserManager", "createUserWithEmail:failure", task.getException());
                            onFailure.runWithMessage("Registration failed");
                        }
                    }
                });
    }

    boolean addReservations(int reservedShelterId, int reservedVacancies, IMessageable onFailure) {
        return user.addReservations(reservedShelterId, reservedVacancies, onFailure);
    }

    void releaseReservations(int releasedVacancies) {
        user.releaseReservations(releasedVacancies);
    }

    /**
     * Gets the number of reservations by current user at specified shelter
     * @param shelterId ShelterID of shelter to check reservations
     * @return Number of reservations held at specified shelter
     */
    int getReservations(int shelterId) {
        if (shelterId != user.getReservedShelterId())
            return 0;
        return user.getReservedVacancies();
    }


    void signOut() {
        user.signOut();
        user = null;
    }

}
