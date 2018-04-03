package edu.gatech.a2340.shelterme.controller;

import android.support.v7.app.AppCompatActivity;

import edu.gatech.a2340.shelterme.Model.IMessageable;
import edu.gatech.a2340.shelterme.Model.ManagerFacade;
import edu.gatech.a2340.shelterme.R;

public class LoginActivity extends AppCompatActivity {

    // Android widgets for binding and getting information
    private EditText emailInput;
    private EditText passwordInput;

    private boolean loggingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loggingIn = false;

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        Button signInButton = findViewById(R.id.email_sign_in_button);
        Button registerButton = findViewById(R.id.email_register_button);
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
