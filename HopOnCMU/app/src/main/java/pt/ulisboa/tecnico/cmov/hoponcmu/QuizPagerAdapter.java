package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.fragments.QuizQuestionFragment;

public class QuizPagerAdapter extends FragmentStatePagerAdapter {

    private int numPages;
    private List<Question> questions;

    public QuizPagerAdapter(FragmentManager fm, int numPages, List<Question> questions) {
        super(fm);
        this.numPages = numPages;
        this.questions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        return QuizQuestionFragment.newInstance(position, questions.get(position));
    }

    @Override
    public int getCount() {
        return numPages;
    }
}
