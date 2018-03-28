package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.fragments.DownloadsFragment;
import pt.ulisboa.tecnico.cmov.hoponcmu.fragments.MonumentsFragment;
import pt.ulisboa.tecnico.cmov.hoponcmu.fragments.RankingFragment;
import pt.ulisboa.tecnico.cmov.hoponcmu.views.SearchEditText;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    NavigationView mNavigationView;

    SearchEditText mSearch;

    boolean areMenuOptionsVisible = true;
    boolean isBackArrowVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setmSearch();

        setmDrawerLayout();

        if (savedInstanceState == null) {
            //Handle the initial fragment transaction
            // Starting elements viewed on the MainActivity
            mToolbar.setTitle(this.getString(R.string.monuments));
            setSupportActionBar(mToolbar);
            mSearch.setHint(R.string.monument_search_hint);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.flContent, MonumentsFragment.newInstance()).commit();
        }

    }

    private void setmSearch() {
        mSearch = (SearchEditText) findViewById(R.id.search_bar);
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

    private boolean selectDrawerItem(MenuItem menuItem) {
        Fragment fragment;
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
                fragmentClass = MonumentsFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
