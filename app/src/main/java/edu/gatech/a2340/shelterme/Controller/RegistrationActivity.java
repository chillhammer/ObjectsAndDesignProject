package edu.gatech.a2340.shelterme.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.gatech.a2340.shelterme.Model.UserType;
import edu.gatech.a2340.shelterme.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText passwordConfirmInput;
    private Spinner userTypeInput;
    private Button registerButton;

    private Button cancelButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameInput = (EditText) findViewById(R.id.username_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
        passwordConfirmInput = (EditText) findViewById(R.id.passwordconfirm_input);

        //Set up adapter stuff for the spinner
        userTypeInput = (Spinner) findViewById(R.id.user_type_input);
        ArrayAdapter<UserType> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, UserType.values());
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

        mAuth = FirebaseAuth.getInstance();

    }

    public void onRegisterPressed() {
        if (passwordInput.getText().toString().equals(passwordConfirmInput.getText().toString())) {
            // Brandon, the string values below represent the user's input
            String usernameValue = usernameInput.getText().toString();
            String passwordValue = passwordInput.getText().toString();
            boolean userIsAdmin = isAdmin; // required by M5

        } else { // password and confirm password don't match
            Toast.makeText(getApplicationContext(),
                    "Password and confirm password must match.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void attemptFirebaseRegistration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Registration successful, replace registration activity with main
                            //with user logged in
                        } else {
                            //Registration failed
                            Toast.makeText(RegistrationActivity.this, "Registration failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
