package edu.gatech.a2340.shelterme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText passwordConfirmInput;
    private Switch isAdminInput;
    private boolean isAdmin;
    private Button registerButton;

    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameInput = (EditText) findViewById(R.id.username_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
        passwordConfirmInput = (EditText) findViewById(R.id.passwordconfirm_input);
        isAdminInput = (Switch) findViewById(R.id.admin_input);
        registerButton = (Button) findViewById(R.id.registration_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        isAdminInput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isAdmin = isChecked;
            }
        });

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

}
