package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.DownloadQuizResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.HelloResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.TransactionRepository;
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
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    NavigationView mNavigationView;
    SearchEditText mSearch;
    boolean areMenuOptionsVisible = true;
    boolean isBackArrowVisible = false;
    ManagerFragment fragment;
    // Location classes
    private boolean mRequestingLocationUpdates = true;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private TransactionRepository transactionRepository;

    public static Location getmLastLocation() {
        return mLastLocation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transactionRepository = new TransactionRepository(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this);

        fragment = MonumentsFragment.newInstance();

        setmSearch();

        setmDrawerLayout();

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
                    mToolbar.setNavigationIcon(R.drawable.ic_menu_white);
                    setSupportActionBar(mToolbar);
                    mSearch.setVisibility(View.GONE);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.action_search:
                areMenuOptionsVisible = false;
                isBackArrowVisible = true;
                mSearch.setText("");
                mSearch.setVisibility(View.VISIBLE);
                mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
                setSupportActionBar(mToolbar);
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

    private void setmSearch() {
        mSearch = (SearchEditText) findViewById(R.id.search_bar);
        mSearch.addTextChangedListener(new TextWatcher() {
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
        mSearch.setDrawableClickListener(new SearchEditText.DrawableClickListener() {

            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        mSearch.setText("");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setmDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setmToolbar();
        setmNavigationView();
    }

    private void setmToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
    }

    private void setmNavigationView() {
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return selectDrawerItem(menuItem);
                    }
                });
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
                mToolbar.setTitle(this.getString(R.string.monuments));
                mSearch.setHint(R.string.monument_search_hint);
                fragmentClass = MonumentsFragment.class;
                break;
            case R.id.nav_rankings:
                mToolbar.setTitle(this.getString(R.string.ranking));
                mSearch.setHint(R.string.username_search_hint);
                fragmentClass = RankingFragment.class;
                break;
            case R.id.nav_downloads:
                mToolbar.setTitle(this.getString(R.string.downloads));
                mSearch.setHint(R.string.monument_search_hint);
                fragmentClass = DownloadsFragment.class;
                break;
            case R.id.nav_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_logout:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                mToolbar.setTitle(this.getString(R.string.monuments));
                mSearch.setHint(R.string.monument_search_hint);
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
            mToolbar.setTitle(this.getString(R.string.monuments));
            setSupportActionBar(mToolbar);
            mSearch.setHint(R.string.monument_search_hint);
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
        } else if (response instanceof DownloadQuizResponse) {
            DownloadQuizResponse downloadQuizResponse = (DownloadQuizResponse) response;
            transactionRepository.insertQuizAndQuestions(
                    downloadQuizResponse.getQuiz(), downloadQuizResponse.getQuestions());
        }
    }

    @Override
    public void onTaskCompleted(Location location) {
        mLastLocation = location;
        fragment.refreshMonuments();
    }
}
