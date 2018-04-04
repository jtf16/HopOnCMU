package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.QuizPagerAdapter;
import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;

public class QuizActivity extends ManagerActivity {

    public static final String ARG_QUESTIONS = "questions";
    Toolbar mToolbar;
    private List<Question> questions;
    private int totalQuestions;
    private EditText questionNumber;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setmToolbar();

        questions = (List<Question>) getIntent().getSerializableExtra(ARG_QUESTIONS);
        totalQuestions = questions.size();
        ((TextView) findViewById(R.id.total_question_numbers))
                .setText(Integer.toString(totalQuestions));
        questionNumber = (EditText) findViewById(R.id.question_number);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new QuizPagerAdapter(
                getSupportFragmentManager(), totalQuestions, questions);
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                questionNumber.setText(Integer.toString(position + 1));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setmToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
    }

    public void submit(View view) {
        // TODO: Submit quiz when button clicked
    }

    public void goLeft(View view) {
        if (mPager.getCurrentItem() > 0) {
            questionNumber.setText(Integer.toString(mPager.getCurrentItem() - 1));
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void goRight(View view) {
        if (mPager.getCurrentItem() < totalQuestions) {
            questionNumber.setText(Integer.toString(mPager.getCurrentItem() + 1));
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        }
    }

    @Override
    public void updateInterface(Response response) {

    }
}
