package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.QuizPagerAdapter;
import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.SubmitQuizCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class QuizActivity extends ManagerActivity {

    public static final String ARG_QUESTIONS = "questions";
    public static final String ARG_QUIZ = "quiz";
    public static User user;
    Toolbar mToolbar;
    List<TextView> pagination = new ArrayList<TextView>();
    private List<Question> questions;
    private Quiz quiz;
    private int totalQuestions;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private SharedPreferences pref;
    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = pref.getString(LoginActivity.USER, "");
        user = gson.fromJson(json, User.class);

        questions = (List<Question>) getIntent().getSerializableExtra(ARG_QUESTIONS);
        quiz = (Quiz) getIntent().getSerializableExtra(ARG_QUIZ);

        setmToolbar();
        setClock();

        pagination.add((TextView) findViewById(R.id.pagination1));
        pagination.add((TextView) findViewById(R.id.pagination2));
        pagination.add((TextView) findViewById(R.id.pagination3));
        pagination.add((TextView) findViewById(R.id.pagination4));
        pagination.add((TextView) findViewById(R.id.pagination5));
        pagination.add((TextView) findViewById(R.id.pagination6));
        pagination.add((TextView) findViewById(R.id.pagination7));

        totalQuestions = questions.size();

        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new QuizPagerAdapter(
                getSupportFragmentManager(), totalQuestions, questions);
        mPager.setAdapter(mPagerAdapter);

        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                int diffPages = Integer.valueOf(pagination.get(3).getText().toString()) - position - 1;
                updatePagination(-diffPages);
            }
        });

        updatePagination(0);
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
        getSupportActionBar().setTitle(quiz.getName());
    }

    private void setClock() {
        chronometer = (Chronometer) findViewById(R.id.clock);
        long startingTime =
                Calendar.getInstance().getTime().getTime() - quiz.getOpenTime().getTime();
        chronometer.setBase(SystemClock.elapsedRealtime() - startingTime);
        chronometer.start();
    }

    public void submit(View view) {
        Snackbar snackbar = Snackbar.make(view, R.string.quiz_submited, Snackbar.LENGTH_SHORT);
        View snackbarView = (View) snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorSnackbar));
        snackbar.setActionTextColor(getResources().getColor(R.color.colorUndoText));
        snackbar.setAction(R.string.quiz_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Doesn't submit the quiz
            }
        });
        snackbar.show();

        quiz.setSubmitTime(Calendar.getInstance().getTime());
        long sessionId = pref.getLong(LoginActivity.SESSION_ID, -1);
        SubmitQuizCommand sqc = new SubmitQuizCommand(
                user.getUsername(), sessionId, quiz, questions);
        new CommunicationTask(this, sqc).execute();
    }

    public void goLeft(View view) {
        mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }

    public void goRight(View view) {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    public void jumpPages(View view) {

        TextView page = (TextView) view;

        int diffPages = Integer.valueOf(pagination.get(3).getText().toString()) - Integer.valueOf(page.getText().toString());

        mPager.setCurrentItem(mPager.getCurrentItem() - diffPages);
    }

    public void updatePagination(int hops) {

        for (TextView textView : pagination) {
            int pageValue = Integer.valueOf(textView.getText().toString()) + hops;

            if (pageValue >= 1 && pageValue <= totalQuestions) {
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.INVISIBLE);
            }

            textView.setText(pageValue + "");

            int questionNumber = Integer.valueOf(textView.getText().toString()) - 1;
            if (questionNumber >= 0 && questionNumber < totalQuestions && questions.get(questionNumber).getAnswer() != null) {
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                textView.setTextColor(getResources().getColor(R.color.colorPagination));
            }
        }
    }

    @Override
    public void updateInterface(Response response) {

    }
}
