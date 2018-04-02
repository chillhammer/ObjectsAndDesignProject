package edu.gatech.a2340.shelterme.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.gatech.a2340.shelterme.Model.IMessageable;
import edu.gatech.a2340.shelterme.Model.ManagerFacade;
import edu.gatech.a2340.shelterme.R;

public class LoginActivity extends AppCompatActivity {

    // Android widgets for binding and getting information
    private EditText emailInput;
    private EditText passwordInput;
    private Button signInButton;
    private Button registerButton;

    private boolean loggingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loggingIn = false;

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        signInButton = findViewById(R.id.email_sign_in_button);
        registerButton = findViewById(R.id.email_register_button);
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
     * Validates the input, then attempts to login with firebase authentication
     */
    private void onSignInPressed() {
        String email = emailInput.getText().toString();
        String pass = passwordInput.getText().toString();
        if (!loggingIn && hasWindowFocus()) {
            loggingIn = true;
            ManagerFacade.getInstance().attemptSignIn(email, pass,
                    //On success
                    new IMessageable() {
                        @Override
                        public void runWithMessage(String message) {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            loggingIn = false;
                        }
                    },
                    //On failure
                    new IMessageable() {
                        @Override
                        public void runWithMessage(String message) {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            loggingIn = false;
                        }
                    });
        }
    }

}
