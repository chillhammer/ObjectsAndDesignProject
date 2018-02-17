package edu.gatech.a2340.shelterme.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import edu.gatech.a2340.shelterme.Model.User;
import edu.gatech.a2340.shelterme.Model.UserType;
import edu.gatech.a2340.shelterme.R;

public class MainActivity extends AppCompatActivity {

    private Button logoutButton;
    private TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeTextView = (TextView) findViewById(R.id.welcomeUserText);
        welcomeTextView.setText("Welcome " + User.userType.toString() + " " + User.userEmail);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                User.userType = UserType.ANONYMOUS;
                User.userEmail = "NoEmail";
                finish();
            }
        });
    }
}
