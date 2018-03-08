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

import edu.gatech.a2340.shelterme.Model.IMessageable;
import edu.gatech.a2340.shelterme.Model.ManagerFacade;
import edu.gatech.a2340.shelterme.Model.UserManager;
import edu.gatech.a2340.shelterme.Model.UserType;
import edu.gatech.a2340.shelterme.R;

public class LoginActivity extends AppCompatActivity {

    // Android widgets for binding and getting information
    private EditText emailInput;
    private EditText passwordInput;
    private Button signInButton;
    private Button registerButton;

    boolean loggingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
     * Validates the input, then attempts to login with firebase authentication
     */
    public void onSignInPressed() {
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
