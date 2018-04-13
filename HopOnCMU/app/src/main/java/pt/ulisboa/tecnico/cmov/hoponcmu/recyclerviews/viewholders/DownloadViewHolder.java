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
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.QuestionsByQuizIdLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

public class DownloadViewHolder extends RecyclerView.ViewHolder
        implements LoaderManager.LoaderCallbacks<List<Question>> {

    public static final String ARG_QUESTIONS = "questions";
    private static final int LOADER_QUESTIONS = 1;
    private TextView monumentName;
    private TextView name;
    private Quiz quiz;
    private Context mContext;
    private DownloadViewHolder mLoader = this;

    public DownloadViewHolder(final Context context, View itemView, final LoaderManager loader) {
        super(itemView);
        mContext = context;
        // Define click listener for the ViewHolder's View.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(DownloadViewHolder.class.getName(),
                        "Element " + getAdapterPosition() + " clicked.");

                loader.restartLoader(LOADER_QUESTIONS, null, mLoader);
            }
        });
        monumentName = itemView.findViewById(R.id.monument_name);
        name = itemView.findViewById(R.id.download_name);
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        name.setText(quiz.getName());
    }

    public void setMonumentName(Monument monument) {
        monumentName.setText(monument.getName());
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
