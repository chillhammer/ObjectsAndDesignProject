package edu.gatech.a2340.shelterme.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.gatech.a2340.shelterme.Model.IMessageable;
import edu.gatech.a2340.shelterme.Model.ManagerFacade;
import edu.gatech.a2340.shelterme.Model.Shelter;
import edu.gatech.a2340.shelterme.R;

@SuppressWarnings("FeatureEnvy")
public class DetailsActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView capacityView;
    private TextView restrictionsView;
    private TextView longView;
    private TextView latView;
    private TextView addressView;
    private TextView phoneView;
    private TextView reservationsView;

    private int shelterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameView = findViewById(R.id.shelterName);
        capacityView = findViewById(R.id.shelterCapacity);
        restrictionsView = findViewById(R.id.shelterRestrictions);
        longView = findViewById(R.id.shelterLong);
        latView = findViewById(R.id.shelterLat);
        addressView = findViewById(R.id.shelterAddress);
        phoneView = findViewById(R.id.shelterPhone);
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        reservationsView = findViewById(R.id.yourReservations);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            assert b.get("shelter") != null;
            shelterId = ((Shelter) b.get("shelter")).getId();
            updateUI();
        }


    }

    private void updateUI() {
        ManagerFacade facade = ManagerFacade.getInstance();
        nameView.setText(facade.getShelterList().get(shelterId).getName());
        capacityView.setText("Capacity: " + facade.getShelterList().get(shelterId).getVacancies()
                + "/" + facade.getShelterList().get(shelterId).getCapacity());
        restrictionsView.setText("Restrictions: " +
                facade.getShelterList().get(shelterId).getRestrictions());
        longView.setText("Longitude: " + facade.getShelterList().get(shelterId).getLongitude());
        latView.setText("Latitude: " + facade.getShelterList().get(shelterId).getLatitude());
        addressView.setText("Address: " + facade.getShelterList().get(shelterId).getAddress());
        phoneView.setText("Phone number: " + facade.getShelterList().get(shelterId).getPhone());
        reservationsView.setText("Your Reservations: "
                + ManagerFacade.getInstance().getReservations(facade.getShelterList().
                get(shelterId).getId()));
    }

    /**
     * Called by increment button
     * @param view view to add reservation to
     */
    public void addReservation(View view) {
        ManagerFacade.getInstance().addReservations(shelterId,
                new IMessageable() {
                    @Override
                    public void runWithMessage(String message) {
                        Toast.makeText(DetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
        updateUI();
    }

    /**
     * Called by decrement button
     * @param view view to remove reservation from
     */
    public void removeReservation(View view) {
        ManagerFacade.getInstance().releaseReservations(shelterId,
                new IMessageable() {
                    @Override
                    public void runWithMessage(String message) {
                        Toast.makeText(DetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
        updateUI();
    }

}
