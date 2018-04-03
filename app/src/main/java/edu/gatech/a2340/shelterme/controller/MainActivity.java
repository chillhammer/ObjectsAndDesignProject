package edu.gatech.a2340.shelterme.controller;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.a2340.shelterme.Model.ManagerFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import edu.gatech.a2340.shelterme.Model.Shelter;
import edu.gatech.a2340.shelterme.Model.ShelterQueryComparator;
import edu.gatech.a2340.shelterme.R;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private List<Shelter> searchBuffer;
    private ManagerFacade facade;
    private ArrayAdapter<Shelter> adapter;
    private Boolean drawerDefined = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        facade = ManagerFacade.getInstance();

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                ManagerFacade.getInstance().getShelterList());
        listView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Set up auto updating of the model whenever shelter data changes
        if (getIntent().getSerializableExtra("SEARCH_RESULTS") != null) {
            adapter.clear();
            adapter.addAll((ArrayList<Shelter>) getIntent().getSerializableExtra("SEARCH_RESULTS"));
            adapter.notifyDataSetChanged();
        } else {
            database.getReference().child("shelters").addValueEventListener(
                    new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    ManagerFacade.getInstance().updateShelterList(snapshot);
                    adapter.clear();
                    adapter.addAll(ManagerFacade.getInstance().getShelterList());
                    adapter.notifyDataSetChanged();
                    searchBuffer = facade.getShelterList();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("MainActivity", databaseError.toException());
                }
            });

        }

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(android.R.drawable.ic_menu_more);

        mDrawerLayout.closeDrawers();

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                        if(!drawerDefined) {
                            CheckBox other = findViewById(R.id.nav_other);
                            CheckBox anyone = findViewById(R.id.nav_anyone);
                            other.setChecked(true);
                            anyone.setChecked(true);
                            drawerDefined = true;
                        }
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                        CheckBox male = findViewById(R.id.nav_male);
                        CheckBox female = findViewById(R.id.nav_female);
                        CheckBox other = findViewById(R.id.nav_other);

                        CheckBox family = findViewById(R.id.nav_family);
                        CheckBox children = findViewById(R.id.nav_children);
                        CheckBox young_adults = findViewById(R.id.nav_young_adults);
                        CheckBox anyone = findViewById(R.id.nav_anyone);

                        searchBuffer.clear();

                        for (Shelter shelter : facade.getShelterList()) {
                            if (!other.isChecked()) {
                                if (female.isChecked() && shelter.getRestrictions().
                                        contains("Women")) {
                                    searchBuffer.add(shelter);
                                    continue;
                                }
                                if (male.isChecked() && shelter.getRestrictions().contains("Men")) {
                                    searchBuffer.add(shelter);
                                    continue;
                                }
                            }
                            if (!anyone.isChecked()) {
                                if (family.isChecked() && shelter.getRestrictions().
                                        contains("Families")) {
                                    searchBuffer.add(shelter);
                                    continue;
                                }
                                if (children.isChecked() && shelter.getRestrictions().
                                        contains("Children")) {
                                    searchBuffer.add(shelter);
                                    continue;
                                }
                                if (young_adults.isChecked() && shelter.getRestrictions().
                                        contains("Young adults")) {
                                    searchBuffer.add(shelter);
                                    continue;
                                }
                            }
                            if (other.isChecked() && anyone.isChecked()) {
                                searchBuffer.add(shelter);
                            }
                        }

                        adapter.clear();
                        adapter.addAll(searchBuffer);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        handleIntent(getIntent());

        //MICHAEL: CHANGE THIS
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("shelter", ((Shelter) adapterView.getItemAtPosition(position)));
                startActivity(intent);
            }
        });

//        welcomeTextView = (TextView) findViewById(R.id.welcomeUserText);
//        welcomeTextView.setText("Welcome " + User.userType.toString() + " " + User.userEmail);

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /***
     * Calls fuzzy search on new intent from ACTION_SEARCH
     * query results
     * @param intent a reference to the new intent created
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        intent.putExtra("SEARCH_RESULTS", (Serializable) searchBuffer);
    }

    /**
     * Restores last dataset
     */
    @Override
    protected void onResume(){
        super.onResume();
        if (searchBuffer != null) {
            adapter.clear();
            adapter.addAll(searchBuffer);
            adapter.notifyDataSetChanged();
        } else {
            adapter.clear();
            adapter.addAll(facade.getShelterList());
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Converts a list collection into its toString form
     * @param list the list to be converted
     * @return a list of stringified objects
     */
    private List<String> getStringList(List<? extends Object> list) {
        List<String> strings = new ArrayList<>(list.size());
        for (Object object : list) {
            strings.add(Objects.toString(object, null));
        }
        return strings;
    }

    /**
     * Handles the intent that ACTION_SEARCH creates. Implements
     * fuzzy search on query and then saves the results order to
     * realDatset
     * @param intent the new intent created
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction()) && searchBuffer != null) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            List<String> dataList = getStringList(facade.getShelterList());
            List<ExtractedResult> searchResults = FuzzySearch.extractSorted(query, dataList);
            Collections.sort(searchBuffer, new ShelterQueryComparator(searchResults));
            adapter.clear();
            adapter.addAll(ManagerFacade.getInstance().getShelterList());
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Tells the search menun what to do
     * @param menu the search menu created
     * @return status of creation
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        // Set search focus when clicked
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(false);
        searchView.setFocusable(true);
        searchView.requestFocus();

        return true;
    }

    @Override
    protected void onDestroy() {
        ManagerFacade.getInstance().signOut();
        super.onDestroy();
    }


    public void showMap(View view) {
        Intent intent = new Intent(MainActivity.this, GoogleMapsActivity.class);
        intent.putExtra("shelters", (Serializable) searchBuffer);
        startActivity(intent);

    }
}
