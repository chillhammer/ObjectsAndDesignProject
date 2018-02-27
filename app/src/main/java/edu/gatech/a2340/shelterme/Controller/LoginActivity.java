package edu.gatech.a2340.shelterme.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import edu.gatech.a2340.shelterme.Model.User;
import edu.gatech.a2340.shelterme.Model.UserType;
import edu.gatech.a2340.shelterme.R;

public class LoginActivity extends AppCompatActivity {

    // Android widgets for binding and getting information
    private EditText emailInput;
    private EditText passwordInput;
    private Button signInButton;
    private Button registerButton;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    boolean loggingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        loggingIn = false;

        emailInput = (EditText) findViewById(R.id.email_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
        signInButton = (Button) findViewById(R.id.email_sign_in_button);
        registerButton = (Button) findViewById(R.id.email_register_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignInPressed();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Validates the input then attempts to login with firebase authentication
     */
    public void onSignInPressed() {

        if (!loggingIn && hasWindowFocus()) {
            loggingIn = true;
            //Validate input
            String email = emailInput.getText().toString();
            String pass = passwordInput.getText().toString();
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please input an email and password", Toast.LENGTH_SHORT).show();
                loggingIn = false;
                return;
            }

            //Do actual firebase login
            attemptFirebaseLogin(email, pass);
        }
    }

    /**
     * Attempts to log in through firebase authentication
     * and puts user information into static user class
     * @param email User email
     * @param password User password
     */
    private void attemptFirebaseLogin(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            FirebaseUser user = auth.getCurrentUser();
                            //I hate that firebase makes you create a listener JUST TO GET DATA
                            database.getReference().child("users").child(user.getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            User.userEmail = (String) dataSnapshot.child("email").getValue();
                                            User.userType = UserType.valueOf(((String) dataSnapshot.child("userType").getValue()));
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {}
                                    });
                        } else {
                            //Sign in failed, display message
                            Log.w("LoginActivity", "Email and password login failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                        }
                        loggingIn = false;
                    }
                });
    }

}
