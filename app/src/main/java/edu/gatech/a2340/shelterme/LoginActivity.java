package edu.gatech.a2340.shelterme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    // Android widgets for binding and getting information
    private EditText _emailInput;
    private EditText _passwordInput;
    private Button _signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _emailInput = (EditText) findViewById(R.id.email_input);
        _passwordInput = (EditText) findViewById(R.id.password_input);
        _signInButton = (Button) findViewById(R.id.email_sign_in_button);
        _signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignInPressed();
            }
        });
    }

    public void onSignInPressed() {
        if (_emailInput.getText().toString().equals("user")
                && _passwordInput.getText().toString().equals("pass")) {
            Toast.makeText(getApplicationContext(), "Redirecting...",
                    Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect username or password.",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
