package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.RankingCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.HelloResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.RankingResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.UserRepository;
import pt.ulisboa.tecnico.cmov.hoponcmu.fragments.DownloadsFragment;
import pt.ulisboa.tecnico.cmov.hoponcmu.fragments.ManagerFragment;
import pt.ulisboa.tecnico.cmov.hoponcmu.fragments.MonumentsFragment;
import pt.ulisboa.tecnico.cmov.hoponcmu.fragments.RankingFragment;
import pt.ulisboa.tecnico.cmov.hoponcmu.location.FetchCoordinatesTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.views.SearchEditText;

public class MainActivity extends ManagerActivity implements
        FetchCoordinatesTask.OnTaskCompleted {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String REQUESTING_LOCATION_UPDATES_KEY = "tracking_location";
    private static Location mLastLocation = null;

    public User user;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    SearchEditText searchEditText;

    boolean areMenuOptionsVisible = true;
    boolean isBackArrowVisible = false;

    ManagerFragment fragment;
    // Location classes
    private boolean mRequestingLocationUpdates = true;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;

    private UserRepository userRepository;
    private SharedPreferences pref;

    public static Location getmLastLocation() {
        return mLastLocation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = pref.getString(LoginActivity.USER, "");
        user = gson.fromJson(json, User.class);

        userRepository = new UserRepository(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this);

        fragment = MonumentsFragment.newInstance();

        setSearchEditText();

        setDrawerLayout();

        setmLocationCallback();

        updateValuesFromBundle(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRequestingLocationUpdates = true;
        stopLocationUpdates();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
                mRequestingLocationUpdates);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_view, menu);

        // Make all options of the menu invisible
        if (!areMenuOptionsVisible) {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        areMenuOptionsVisible = true;
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isBackArrowVisible) {
                    isBackArrowVisible = false;
                    toolbar.setNavigationIcon(R.drawable.ic_menu_white);
                    setSupportActionBar(toolbar);
                    searchEditText.setVisibility(View.GONE);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.action_search:
                areMenuOptionsVisible = false;
                isBackArrowVisible = true;
                searchEditText.setText("");
                searchEditText.setVisibility(View.VISIBLE);
                toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
                setSupportActionBar(toolbar);
                break;
            case R.id.action_share:
                // TODO: When share button clicked do something
                break;
        }
        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:

                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    mRequestingLocationUpdates = false;
                    stopLocationUpdates();
                }
                break;
        }
    }

    private void setSearchEditText() {
        searchEditText = findViewById(R.id.search_bar);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fragment.refreshSearch(s.toString());
            }
        });
        searchEditText.setDrawableClickListener(new SearchEditText.DrawableClickListener() {

            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        searchEditText.setText("");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_layout);
        setToolbar();
        setNavigationView();
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    private void setNavigationView() {
        navigationView = findViewById(R.id.nav_view);
        setNavigationHeader();
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return selectDrawerItem(menuItem);
                    }
                });
    }

    private void setNavigationHeader() {
        View headerView = navigationView.getHeaderView(0);
        TextView navFullName = headerView.findViewById(R.id.full_name);
        TextView navEmail = headerView.findViewById(R.id.email);
        if (user.getLastName() == null) {
            navFullName.setText(user.getFirstName());
        } else {
            navFullName.setText(getString(R.string.user_full_name,
                    user.getFirstName(), user.getLastName()));
        }
        if (user.getEmail() != null) {
            navEmail.setText(getString(R.string.user_email, user.getEmail()));
            navEmail.setVisibility(View.VISIBLE);
        } else {
            navEmail.setVisibility(View.GONE);
        }
    }

    private void setmLocationCallback() {

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                new FetchCoordinatesTask(MainActivity.this)
                        .execute(locationResult.getLastLocation());
            }
        };
    }

    private boolean selectDrawerItem(MenuItem menuItem) {
        Class fragmentClass;
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.nav_monuments:
                toolbar.setTitle(this.getString(R.string.monuments));
                searchEditText.setHint(R.string.monument_search_hint);
                fragmentClass = MonumentsFragment.class;
                break;
            case R.id.nav_rankings:
                new CommunicationTask(this, new RankingCommand()).execute();
                toolbar.setTitle(this.getString(R.string.ranking));
                searchEditText.setHint(R.string.username_search_hint);
                fragmentClass = RankingFragment.class;
                break;
            case R.id.nav_downloads:
                toolbar.setTitle(this.getString(R.string.downloads));
                searchEditText.setHint(R.string.monument_search_hint);
                fragmentClass = DownloadsFragment.class;
                break;
            case R.id.nav_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_logout:
                SharedPreferences.Editor edit = pref.edit();
                edit.remove(LoginActivity.USER);
                edit.apply();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                toolbar.setTitle(this.getString(R.string.monuments));
                searchEditText.setHint(R.string.monument_search_hint);
                fragmentClass = MonumentsFragment.class;
        }

        try {
            fragment = (ManagerFragment) fragmentClass.newInstance();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mRequestingLocationUpdates = true;
            mFusedLocationClient.requestLocationUpdates
                    (getLocationRequest(), mLocationCallback, null);
        }
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private LocationRequest getLocationRequest() {
        return (new LocationRequest()).setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            toolbar.setTitle(this.getString(R.string.monuments));
            setSupportActionBar(toolbar);
            searchEditText.setHint(R.string.monument_search_hint);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.flContent, fragment).commit();
            return;
        }

        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
            mRequestingLocationUpdates = savedInstanceState.getBoolean(
                    REQUESTING_LOCATION_UPDATES_KEY);
        }
    }

    @Override
    public void updateInterface(Response response) {
        if (response instanceof HelloResponse) {
            Log.d("HelloResponse", ((HelloResponse) response).getMessage());
        } else if (response instanceof RankingResponse) {
            RankingResponse rankingResponse = (RankingResponse) response;
            userRepository.insertUser(rankingResponse.getUsers());
            fragment.refreshRanking();
        }
    }

    @Override
    public void onTaskCompleted(Location location) {
        mLastLocation = location;
        fragment.refreshMonuments();
    }
}
