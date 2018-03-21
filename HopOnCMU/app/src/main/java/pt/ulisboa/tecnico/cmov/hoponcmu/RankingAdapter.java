package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import pt.ulisboa.tecnico.cmov.hoponcmu.fragments.AllTimeRankingFragment;
import pt.ulisboa.tecnico.cmov.hoponcmu.fragments.DayRankingFragment;
import pt.ulisboa.tecnico.cmov.hoponcmu.fragments.MonthRankingFragment;
import pt.ulisboa.tecnico.cmov.hoponcmu.fragments.WeekRankingFragment;

public class RankingAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public RankingAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return DayRankingFragment.newInstance();
        } else if (position == 1) {
            return WeekRankingFragment.newInstance();
        } else if (position == 2) {
            return MonthRankingFragment.newInstance();
        } else {
            return AllTimeRankingFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.day);
        } else if (position == 1) {
            return mContext.getString(R.string.week);
        } else if (position == 2) {
            return mContext.getString(R.string.month);
        } else {
            return mContext.getString(R.string.all_time);
        }
    }
}
