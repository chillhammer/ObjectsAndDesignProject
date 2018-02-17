package edu.gatech.a2340.shelterme.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.gatech.a2340.shelterme.Model.UserType;
import edu.gatech.a2340.shelterme.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private EditText passwordConfirmInput;
    private Spinner userTypeInput;
    private Button registerButton;

    private Button cancelButton;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        emailInput = (EditText) findViewById(R.id.email_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
        passwordConfirmInput = (EditText) findViewById(R.id.passwordconfirm_input);

        //Set up adapter stuff for the user type spinner
        userTypeInput = (Spinner) findViewById(R.id.user_type_input);
        ArrayAdapter<UserType> typeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, UserType.getValues());
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeInput.setAdapter(typeAdapter);

        registerButton = (Button) findViewById(R.id.registration_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterPressed();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        auth.signOut();
        database = FirebaseDatabase.getInstance();

    }

    /**
     * Validates the input, then attempts to register with firebase authentication
     */
    public void onRegisterPressed() {
        String emailValue = emailInput.getText().toString();
        String passwordValue = passwordInput.getText().toString();
        UserType typeValue = (UserType) userTypeInput.getSelectedItem();
        if (passwordValue.equals(passwordConfirmInput.getText().toString())) {
            if (emailValue.isEmpty() || passwordValue.isEmpty()) {
                Toast.makeText(this,
                        "All fields must be completed", Toast.LENGTH_SHORT);
            } else {
                attemptFirebaseRegistration(emailValue, passwordValue, typeValue);
            }
        } else { // password and confirm password don't match
            Toast.makeText(getApplicationContext(),
                    "Password and confirm password must match.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Attempts to register new user with firebase authentication
     * and put their information in the database
     * @param email Email of new user
     * @param password Password of new user
     * @param userType UserType of new user
     */
    private void attemptFirebaseRegistration(final String email, String password, final UserType userType) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Registration successful, setup user information in database
                            FirebaseUser user = auth.getCurrentUser();
                            DatabaseReference ref = database.getReference().child("users").child(user.getUid());
                            ref.child("email").setValue(email);
                            ref.child("userType").setValue(userType.name());
                            Toast.makeText(RegistrationActivity.this,
                                    "Registration successful, you may now sign in!",
                                    Toast.LENGTH_LONG).show();
                            auth.signOut();
                        } else {
                            //Registration failed
                            Log.w("RegistrationActivity", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Registration failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
