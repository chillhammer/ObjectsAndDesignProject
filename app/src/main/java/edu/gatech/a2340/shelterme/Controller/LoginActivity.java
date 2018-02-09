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

import edu.gatech.a2340.shelterme.R;

public class LoginActivity extends AppCompatActivity {

    // Android widgets for binding and getting information
    private EditText _emailInput;
    private EditText _passwordInput;
    private Button _signInButton;
    private Button _registerButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        _emailInput = (EditText) findViewById(R.id.email_input);
        _passwordInput = (EditText) findViewById(R.id.password_input);
        _signInButton = (Button) findViewById(R.id.email_sign_in_button);
        _registerButton = (Button) findViewById(R.id.email_register_button);
        _signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignInPressed();
            }
        });

        _registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });
    }

    public void onSignInPressed() {
        //Validate input
        String email = _emailInput.getText().toString();
        String pass = _passwordInput.getText().toString();
        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please input an email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        //Do actual firebase stuff
        attemptFirebaseLogin(email, pass);
    }

    private void attemptFirebaseLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Successful sign in, launch main activity
                        } else {
                            //Sign in failed, display message
                            Log.w("LoginActivity", "Email and password login failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
