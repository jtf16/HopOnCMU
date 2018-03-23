package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.RankingAdapter;

public class RankingActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;

    RankingAdapter mRankingAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        setmViewPager();

        setmDrawerLayout();
    }

    private void setmViewPager() {
        mRankingAdapter = new RankingAdapter(
                this, getSupportFragmentManager());

        // Set up the ViewPager, attaching the adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mRankingAdapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setmDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setmToolbar();
    }

    private void setmToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mToolbar.setTitle(this.getString(R.string.ranking));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.START);
    }
}
