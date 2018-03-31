package pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.QuizActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.DatabaseCreator;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.QuestionsByQuizIdLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.QuestionRepository;

public class DownloadViewHolder extends RecyclerView.ViewHolder
        implements LoaderManager.LoaderCallbacks<List<Question>> {

    public static final String ARG_QUESTIONS = "questions";
    private static final int LOADER_QUESTIONS = 1;
    private TextView name;
    private Quiz quiz;
    private Context mContext;
    private DownloadViewHolder mLoader = this;
    private QuestionRepository questionRepository;

    public DownloadViewHolder(final Context context, View itemView, final LoaderManager loader) {
        super(itemView);
        mContext = context;
        questionRepository = new QuestionRepository(context);
        // Define click listener for the ViewHolder's View.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(DownloadViewHolder.class.getName(),
                        "Element " + getAdapterPosition() + " clicked.");

                // TODO: Remove this sample when ready
                Question[] questions = DatabaseCreator.questions;
                for (Question question : questions) {
                    question.setQuizID(quiz.getId());
                }
                questionRepository.insertQuestion(questions);

                loader.restartLoader(LOADER_QUESTIONS, null, mLoader);
            }
        });
        name = itemView.findViewById(R.id.download_name);
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        name.setText(quiz.getName());
    }

    @Override
    public Loader<List<Question>> onCreateLoader(int id, Bundle args) {
        return new QuestionsByQuizIdLoader(mContext, quiz.getId());
    }

    @Override
    public void onLoadFinished(Loader<List<Question>> loader, List<Question> data) {
        if (data.size() > 0) {
            Intent intent = new Intent(mContext, QuizActivity.class);
            intent.putExtra(ARG_QUESTIONS, (Serializable) data);
            mContext.startActivity(intent);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Question>> loader) {

    }
}
