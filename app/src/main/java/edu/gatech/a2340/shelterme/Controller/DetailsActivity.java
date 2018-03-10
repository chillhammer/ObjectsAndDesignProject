package edu.gatech.a2340.shelterme.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.gatech.a2340.shelterme.R;

public class DetailsActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView capacityView;
    private TextView restrictionsView;
    private TextView longView;
    private TextView latView;
    private TextView addressView;
    private TextView phoneView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameView = (TextView) findViewById(R.id.shelterName);
        capacityView = (TextView) findViewById(R.id.shelterCapacity);
        restrictionsView = (TextView) findViewById(R.id.shelterRestrictions);
        longView = (TextView) findViewById(R.id.shelterLong);
        latView = (TextView) findViewById(R.id.shelterLat);
        addressView = (TextView) findViewById(R.id.shelterAddress);
        phoneView = (TextView) findViewById(R.id.shelterPhone);
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            nameView.setText((String) b.get("name"));
            capacityView.setText("Vacancies: " + (Integer)b.get("vacancies") + "/" + (Integer) b.get("capacity"));
            restrictionsView.setText("Restrictions: " + (String) b.get("restrictions"));
            longView.setText("Longitude: " + (String) b.get("long"));
            latView.setText("Latitude: " + (String) b.get("lat"));
            addressView.setText("Address: " + (String) b.get("address"));
            phoneView.setText("Phone number: " + (String) b.get("phone"));

        }


    }

}
