package edu.gatech.a2340.shelterme.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Brandon on 3/12/18.
 */

class User {
    private String userId;
    private int reservedShelterId;
    private int reservedVacancies;


    /**
     * Attempts to log in through firebase authentication
     * and grab user information
     * @param email User email
     * @param password User password
     * @param onSuccess Callback for successful login
     * @param onFailure Callback for unsuccessful login
     */
    User(String email, String password, final IMessageable onSuccess,
         final IMessageable onFailure) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //This calls the method passed in as a parameter
                            onSuccess.runWithMessage("Login successful");
                            final FirebaseUser firebaseUser = auth.getCurrentUser();

                            //Grab the user's data
                            assert firebaseUser != null;
                            database.getReference().child("users").child(firebaseUser.getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            userId = firebaseUser.getUid();
                                            Long temp = (Long) dataSnapshot.child(
                                                    "reservations/shelterId").getValue();
                                            reservedShelterId = temp == null ? -1 : temp.intValue();
                                            temp = (Long) dataSnapshot.child(
                                                    "reservations/reservedVacancies").getValue();
                                            reservedVacancies = temp == null ? 0 : temp.intValue();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {}
                                    });
                        } else {
                            onFailure.runWithMessage("Error logging in. User may not exist.");
                            Log.w("User", "Email and password login failed", task.getException());
                        }
                    }
                });
    }

    void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    /**
     * Releases number of reservations from the corresponding shelter
     * @param reservedShelterId ID of shelter to reserve vacancies from
     * @param reservedVacancies Number of vacancies to reserve
     */
    void addReservations(int reservedShelterId, int reservedVacancies) {
        updateReservation(reservedShelterId, this.reservedVacancies +
                reservedVacancies);
    }

    /**
     * Check if reservations can be made given current user state
     * @param reservedShelterId ID of shelter to check reservations from
     * @param reservedVacancies Number of vacancies to check
     * @param onFailure Callback depending on which check fails
     * @return true if the reservation is valid, else false
     */
    boolean validateReservations(int reservedShelterId, int reservedVacancies, IMessageable
            onFailure) {
        if (reservedShelterId != this.reservedShelterId && this.reservedVacancies > 0) {
            onFailure.runWithMessage("You already have reservations at another shelter");
            return false;
        }
        return true;
    }

    /**
     * Attempts to release number of reservations from the corresponding shelter
     * @param reservedShelterId ID of shelter to release reservations from
     * @param releasedVacancies Number of reservations to release
     */
    void releaseReservations(int reservedShelterId, int releasedVacancies) {
        updateReservation(this.reservedShelterId, this.reservedVacancies -
                releasedVacancies);
    }

    /**
     * Check if reservations can be made given current user state
     * @param reservedShelterId ID of shelter to check reservations from
     * @param releasedVacancies Number of vacancies to check
     * @param onFailure Callback depending on which check fails
     * @return true if the release is valid, else false
     */
    boolean validateRelease(int reservedShelterId, int releasedVacancies, IMessageable onFailure) {
        if (reservedShelterId != this.reservedShelterId || this.reservedVacancies -
                releasedVacancies < 0) {
            onFailure.runWithMessage("You do not have any reservations to release");
            return false;
        }
        return true;
    }

    private void updateReservation(int reservedShelterId, int reservedVacancies) {
        this.reservedShelterId = reservedShelterId;
        this.reservedVacancies = reservedVacancies;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("users").
                child(userId).child("reservations");
        ref.child("shelterId").setValue(reservedShelterId);
        ref.child("reservedVacancies").setValue(reservedVacancies);
    }

    // --Commented out by Inspection (4/2/2018 6:04 PM):UserType getUserType() { return userType; }
    // --Commented out by Inspection (4/2/2018 6:04 PM):String getUserId() { return userId; }
    int getReservedShelterId() { return reservedShelterId; }
    int getReservedVacancies() { return reservedVacancies; }

}
