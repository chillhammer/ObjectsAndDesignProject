package edu.gatech.a2340.shelterme.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import edu.gatech.a2340.shelterme.Model.IMessageable;
import edu.gatech.a2340.shelterme.Model.ManagerFacade;
import edu.gatech.a2340.shelterme.Model.UserType;
import edu.gatech.a2340.shelterme.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private EditText passwordConfirmInput;
    private Spinner userTypeInput;
    private Button registerButton;

    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        passwordConfirmInput = findViewById(R.id.passwordconfirm_input);

        //Set up adapter stuff for the user type spinner
        userTypeInput = findViewById(R.id.user_type_input);
        ArrayAdapter<UserType> typeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, UserType.getValues());
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeInput.setAdapter(typeAdapter);

        registerButton = findViewById(R.id.registration_button);
        cancelButton = findViewById(R.id.cancel_button);

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

    /**
     * Validates the input, then attempts to register with firebase authentication
     */
    private void onRegisterPressed() {
        String emailValue = emailInput.getText().toString();
        String passwordValue = passwordInput.getText().toString();
        String confirmPasswordValue = passwordConfirmInput.getText().toString();
        UserType userTypeValue = (UserType) userTypeInput.getSelectedItem();
        ManagerFacade.getInstance().attemptRegister(emailValue, passwordValue,
                confirmPasswordValue,
                userTypeValue,
                new IMessageable() {
                    @Override
                    public void runWithMessage(String message) {
                        Toast.makeText(RegistrationActivity.this,
                                message, Toast.LENGTH_LONG).show();
                    }
                },
                new IMessageable() {
                    @Override
                    public void runWithMessage(String message) {
                        Toast.makeText(RegistrationActivity.this, message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
