package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.SecretKey;

import pt.ulisboa.tecnico.cmov.hoponcmu.QuizPagerAdapter;
import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed.SubmitQuizSealedCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.SubmitQuizResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.sealed.SealedResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.UserQuiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.UserQuizRepository;
import pt.ulisboa.tecnico.cmov.hoponcmu.security.SecurityManager;

public class QuizActivity extends TermiteManagerActivity {

    public static final String ARG_QUESTIONS = "questions";
    public static final String ARG_QUIZ = "quiz";
    public static final String ARG_USER_QUIZ = "user_quiz";

    public User user;
    private UserQuiz userQuiz;
    private Quiz quiz;
    private SecretKey key;

    private List<TextView> pagination = new ArrayList<>();
    private List<Question> questions;

    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;

    private int totalQuestions;

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    private Chronometer chronometer;

    private UserQuizRepository userQuizRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        userQuizRepository = new UserQuizRepository(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LoginActivity.USER, "");
        user = gson.fromJson(json, User.class);
        key = SecurityManager.getSecretKey(sharedPreferences);

        questions = (List<Question>) getIntent().getSerializableExtra(ARG_QUESTIONS);
        quiz = (Quiz) getIntent().getSerializableExtra(ARG_QUIZ);
        userQuiz = (UserQuiz) getIntent().getSerializableExtra(ARG_USER_QUIZ);

        setToolbar();
        setClock();

        pagination.add((TextView) findViewById(R.id.pagination1));
        pagination.add((TextView) findViewById(R.id.pagination2));
        pagination.add((TextView) findViewById(R.id.pagination3));
        pagination.add((TextView) findViewById(R.id.pagination4));
        pagination.add((TextView) findViewById(R.id.pagination5));
        pagination.add((TextView) findViewById(R.id.pagination6));
        pagination.add((TextView) findViewById(R.id.pagination7));

        totalQuestions = questions.size();

        pager = findViewById(R.id.view_pager);
        pagerAdapter = new QuizPagerAdapter(
                getSupportFragmentManager(), totalQuestions, questions, user.getUsername());
        pager.setAdapter(pagerAdapter);

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

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

    private void setToolbar() {
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(quiz.getName());
    }

    private void setClock() {

        TextView score = findViewById(R.id.score);
        Button submit = findViewById(R.id.submit_btn);
        chronometer = findViewById(R.id.clock);

        long openTime = userQuiz.getOpenTime().getTime();
        long submitTime = (userQuiz.getSubmitTime() != null) ?
                userQuiz.getSubmitTime().getTime() : Calendar.getInstance().getTime().getTime();
        long startingTime = submitTime - openTime;

        chronometer.setBase(SystemClock.elapsedRealtime() - startingTime);

        if (userQuiz.getSubmitTime() == null) {
            chronometer.start();
        } else if (userQuiz.getScore() >= 0) {
            chronometer.setVisibility(View.GONE);
            score.setText(getResources().getText(R.string.score) + ": " + userQuiz.getScore());
            score.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        } else {
            submit.setVisibility(View.GONE);
        }
    }

    public void submit(View view) {
        Snackbar snackbar = Snackbar.make(view, R.string.quiz_submitted, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorSnackbar));
        snackbar.setActionTextColor(getResources().getColor(R.color.colorUndoText));
        snackbar.setAction(R.string.quiz_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Doesn't submit the quiz
            }
        });
        snackbar.show();

        userQuiz.setSubmitTime(Calendar.getInstance().getTime());
        long sessionId = sharedPreferences.getLong(LoginActivity.SESSION_ID, -1);
        //SubmitQuizCommand sqc = new SubmitQuizCommand(sessionId, userQuiz, questions);
        SubmitQuizSealedCommand sqsc = new SubmitQuizSealedCommand(user.getUsername(),
                key, sessionId, userQuiz, questions);
        new CommunicationTask(this, sqsc).execute();
        finish();
    }

    public void goLeft(View view) {
        pager.setCurrentItem(pager.getCurrentItem() - 1);
    }

    public void goRight(View view) {
        pager.setCurrentItem(pager.getCurrentItem() + 1);
    }

    public void jumpPages(View view) {

        TextView page = (TextView) view;

        int diffPages = Integer.valueOf(pagination.get(3).getText().toString()) - Integer.valueOf(page.getText().toString());

        pager.setCurrentItem(pager.getCurrentItem() - diffPages);
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
                textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            } else {
                textView.setTextColor(getResources().getColor(R.color.colorPagination));
            }
        }
    }

    @Override
    public void updateInterface(Response response) {

        if (response instanceof SealedResponse) {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SecretKey secretKey = SecurityManager.getSecretKey(sharedPreferences);

            SealedResponse sr = (SealedResponse) response;

            Response response1 = (Response) SecurityManager.getObject(sr.getSealedContent(), sr.getDigest(), secretKey);
            if (response1 instanceof SubmitQuizResponse) {
                SubmitQuizResponse submitQuizResponse = (SubmitQuizResponse) response1;
                userQuizRepository.updateUserQuiz(submitQuizResponse.getUserQuiz());
            }
        }
    }
}
