package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.DownloadQuizResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.InvalidQuizExceptionResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.SessionExceptionResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.TransactionRepository;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.adapters.QuizAdapter;

public class MonumentActivity extends TermiteManagerActivity {

    private static final String ARG_QUIZZES = "quizzes";
    private static final String ARG_IDS = "ids";
    private static final String ARG_MONUMENT = "monument";
    Toolbar toolbar;
    private Monument monument;
    private SharedPreferences pref;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private QuizAdapter quizAdapter;

    private List<Quiz> listQuiz;
    private List<Long> listIDs;

    private String search = "";

    private TransactionRepository transactionRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monument);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        transactionRepository = new TransactionRepository(this);

        listQuiz = (List<Quiz>) getIntent().getSerializableExtra(ARG_QUIZZES);
        listIDs = (List<Long>) getIntent().getSerializableExtra(ARG_IDS);
        monument = (Monument) getIntent().getSerializableExtra(ARG_MONUMENT);

        setToolbar();
        setRecyclerView();

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
        getSupportActionBar().setTitle(monument.getName());
    }


    private void setRecyclerView() {

        recyclerView = findViewById(R.id.quiz_list);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        quizAdapter = new QuizAdapter(this, layoutManager);
        recyclerView.setAdapter(quizAdapter);
        quizAdapter.setQuizzes(listQuiz, listIDs);
    }

    @Override
    public void updateInterface(Response response) {
        if (response instanceof DownloadQuizResponse) {
            DownloadQuizResponse downloadQuizResponse = (DownloadQuizResponse) response;
            transactionRepository.insertUserQuizAndQuestions(
                    downloadQuizResponse.getUsername(), downloadQuizResponse.getQuiz(),
                    downloadQuizResponse.getQuestions());
        } else if (response instanceof InvalidQuizExceptionResponse) {
            Toast.makeText(this, ((InvalidQuizExceptionResponse) response)
                    .getMessage(), Toast.LENGTH_SHORT).show();
        } else if (response instanceof SessionExceptionResponse) {
            SharedPreferences.Editor edit = pref.edit();
            edit.remove(LoginActivity.USER);
            edit.apply();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}
