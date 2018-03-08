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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.a2340.shelterme.Model.ManagerFacade;
import edu.gatech.a2340.shelterme.Model.Shelter;
import edu.gatech.a2340.shelterme.R;

public class MainActivity extends AppCompatActivity {

    private Button logoutButton;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        final ArrayAdapter<Shelter> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                ManagerFacade.getInstance().getShelterList());
        listView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Set up auto updating of the model whenever shelter data changes
        database.getReference().child("shelters").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ManagerFacade.getInstance().updateShelterList(snapshot);
                adapter.clear();
                adapter.addAll(ManagerFacade.getInstance().getShelterList());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("MainActivity", databaseError.toException());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("name", ((Shelter) adapterView.getItemAtPosition(position)).getName());
                intent.putExtra("capacity", ((Shelter) adapterView.getItemAtPosition(position)).getCapacity());
                intent.putExtra("restrictions", ((Shelter) adapterView.getItemAtPosition(position)).getRestrictions());
                intent.putExtra("long", ((Shelter) adapterView.getItemAtPosition(position)).getLongitude());
                intent.putExtra("lat", ((Shelter) adapterView.getItemAtPosition(position)).getLatitude());
                intent.putExtra("address", ((Shelter) adapterView.getItemAtPosition(position)).getAddress());
                intent.putExtra("phone", ((Shelter) adapterView.getItemAtPosition(position)).getPhone());
                startActivity(intent);
            }
        });

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
