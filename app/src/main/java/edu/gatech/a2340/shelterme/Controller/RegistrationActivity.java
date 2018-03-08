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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    }

    /**
     * Validates the input, then attempts to register with firebase authentication
     */
    public void onRegisterPressed() {
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
