package edu.gatech.a2340.shelterme.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.gatech.a2340.shelterme.R;

public class DetailsActivity extends AppCompatActivity {

    private TextView capacityView;
    private TextView genderView;
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

        capacityView = (TextView) findViewById(R.id.shelterCapacity);
        genderView = (TextView) findViewById(R.id.shelterGender);
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

        /*
            Below will not be fully functional until one of you implements the intent that launches
            the Details activity. Utilize Intent class's putExtra() method to pass info through the
            intent. See more:
            https://stackoverflow.com/questions/5265913/how-to-use-putextra-and-getextra-for-string-data
         */
        if (b != null) {
            capacityView.setText((String) b.get("capacity"));
            genderView.setText((String) b.get("gender"));
            longView.setText((String) b.get("long"));
            latView.setText((String) b.get("lat"));
            addressView.setText((String) b.get("address"));
            phoneView.setText((String) b.get("phone"));

        }


    }

}
