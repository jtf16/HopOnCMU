package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import pt.ulisboa.tecnico.cmov.hoponcmu.fragments.QuizQuestionFragment;

public class QuizPagerAdapter extends FragmentStatePagerAdapter {

    private int numPages;

    public QuizPagerAdapter(FragmentManager fm, int numPages) {
        super(fm);
        this.numPages = numPages;
    }

    @Override
    public Fragment getItem(int position) {
        return QuizQuestionFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return numPages;
    }
}
