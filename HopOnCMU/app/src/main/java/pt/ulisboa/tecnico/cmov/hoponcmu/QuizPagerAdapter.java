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
    private String username;

    public QuizPagerAdapter(FragmentManager fm, int numPages,
                            List<Question> questions, String username) {
        super(fm);
        this.numPages = numPages;
        this.questions = questions;
        this.username = username;
    }

    @Override
    public Fragment getItem(int position) {
        return QuizQuestionFragment.newInstance(questions.get(position), username, position+1);
    }

    @Override
    public int getCount() {
        return numPages;
    }
}
