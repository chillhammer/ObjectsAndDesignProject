package edu.gatech.a2340.shelterme.Model;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import edu.gatech.a2340.shelterme.Controller.LoginActivity;
import edu.gatech.a2340.shelterme.Controller.MainActivity;

public class UserManager {
    private static final UserManager ourInstance = new UserManager();

    static UserManager getInstance() {
        return ourInstance;
    }

    private UserManager(){}

    private UserType userType = UserType.ANONYMOUS;
    private String userEmail = "NoEmail";


    /**
     * Validates email and password with hardcoded rules
     * @param email Email to validate
     * @param password Password to validate
     * @return false if input fails any test, else true
     */
    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Attempts to log in through firebase authentication
     * and grab user information
     * @param email UserManager email
     * @param password UserManager password
     */
    void attemptFirebaseLogin(String email, String password, final IMessageable onSuccess, final IMessageable onFailure) {
        if (!validateInput(email, password)) {
            onFailure.runWithMessage("Invalid email or password");
            return;
        }
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //This calls the method passed in as a parameter
                            onSuccess.runWithMessage("Login successful");
                            FirebaseUser user = auth.getCurrentUser();

                            //Grab the user's data
                            database.getReference().child("users").child(user.getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            userEmail = (String) dataSnapshot.child("email").getValue();
                                            userType = UserType.valueOf(((String) dataSnapshot.child("userType").getValue()));
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {}
                                    });
                        } else {
                            onFailure.runWithMessage("Error logging in. User may not exist.");
                            Log.w("LoginActivity", "Email and password login failed", task.getException());
                        }
                    }
                });
    }

    void signOut() {
        FirebaseAuth.getInstance().signOut();
        userType = UserType.ANONYMOUS;
        userEmail = "NoEmail";
    }

}
