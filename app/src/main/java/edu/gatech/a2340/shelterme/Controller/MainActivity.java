package edu.gatech.a2340.shelterme.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.a2340.shelterme.Model.ManagerFacade;
import edu.gatech.a2340.shelterme.Model.Shelter;
import edu.gatech.a2340.shelterme.Model.UserManager;
import edu.gatech.a2340.shelterme.Model.UserType;
import edu.gatech.a2340.shelterme.R;

public class MainActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;

    private Button logoutButton;
//    private TextView welcomeTextView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
//    private static List<String> dataset = new ArrayList<>();
    private static List<Shelter> realDataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        final ArrayAdapter<Shelter> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, realDataset);
        listView.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("shelters").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (realDataset.isEmpty()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String name = (String) child.child("name").getValue();
                        String capacity = (String) child.child("capacity").getValue();
                        String gender = (String) child.child("restrictions").getValue();
                        String longitude = (String) child.child("longitude").getValue();
                        String lat = (String) child.child("latitude").getValue();
                        String address = (String) child.child("address").getValue();
                        String phone = (String) child.child("phone_number").getValue();
//                        MainActivity.dataset.add(name);
                        MainActivity.realDataset.add(new Shelter(name, capacity, gender, longitude,
                                lat, address, phone));

                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
            }
        });






        //MICHAEL: CHANGE THIS
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("name", ((Shelter) adapterView.getItemAtPosition(position)).getName());
                intent.putExtra("capacity", ((Shelter) adapterView.getItemAtPosition(position)).getCapacity());
                intent.putExtra("gender", ((Shelter) adapterView.getItemAtPosition(position)).getRestrictions());
                intent.putExtra("long", ((Shelter) adapterView.getItemAtPosition(position)).getLongitude());
                intent.putExtra("lat", ((Shelter) adapterView.getItemAtPosition(position)).getLatitude());
                intent.putExtra("address", ((Shelter) adapterView.getItemAtPosition(position)).getAddress());
                intent.putExtra("phone", ((Shelter) adapterView.getItemAtPosition(position)).getPhone());
                startActivity(intent);
            }
        });

//        welcomeTextView = (TextView) findViewById(R.id.welcomeUserText);
//        welcomeTextView.setText("Welcome " + UserManager.userType.toString() + " " + UserManager.userEmail);

        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        ManagerFacade.getInstance().signOut();
        super.onDestroy();
    }


}
