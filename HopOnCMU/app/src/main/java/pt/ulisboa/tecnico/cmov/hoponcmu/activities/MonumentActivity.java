package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.adapters.QuizAdapter;

public class MonumentActivity extends ManagerActivity {

    private static final String ARG_QUIZZES = "quizzes";
    private static final String ARG_MONUMENT = "monument";

    Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private QuizAdapter quizAdapter;
    private List<Quiz> listQuiz;
    private String search = "";
    private Monument monument;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monument);

        listQuiz = (List<Quiz>) getIntent().getSerializableExtra(ARG_QUIZZES);
        monument = (Monument) getIntent().getSerializableExtra(ARG_MONUMENT);

        setmToolbar();
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

    private void setmToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(monument.getName());
    }


    private void setRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.quiz_list);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        quizAdapter = new QuizAdapter(this, mLayoutManager);
        mRecyclerView.setAdapter(quizAdapter);
        quizAdapter.setQuizzes(listQuiz);
    }

    @Override
    public void updateInterface(Response response) {

    }
}
