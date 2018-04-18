package pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.LoginActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.QuizActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.AnswerByUserAndQuizLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.QuestionsByQuizIdLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.UserQuizByIdAndUserLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Answer;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.UserQuiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.UserQuizRepository;

public class DownloadViewHolder extends RecyclerView.ViewHolder
        implements LoaderManager.LoaderCallbacks {

    public static final String ARG_QUESTIONS = "questions";
    public static final String ARG_QUIZ = "quiz";
    public static final String ARG_USER_QUIZ = "user_quiz";
    private static final int LOADER_QUESTIONS = 1;
    private static final int LOADER_ANSWERS = 2;
    private static final int LOADER_QUIZ = 3;
    LoaderManager loaderManager;
    private TextView monumentName;
    private TextView name;
    private Quiz quiz;
    private UserQuiz userQuiz;
    private Context mContext;
    private DownloadViewHolder mLoader = this;
    private User user;
    private List<Question> questions;
    private UserQuizRepository userQuizRepository;

    public DownloadViewHolder(final Context context, View itemView, final LoaderManager loader) {
        super(itemView);
        mContext = context;
        loaderManager = loader;

        userQuizRepository = new UserQuizRepository(context);

        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = pref.getString(LoginActivity.USER, "");
        user = gson.fromJson(json, User.class);

        // Define click listener for the ViewHolder's View.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(DownloadViewHolder.class.getName(),
                        "Element " + getAdapterPosition() + " clicked.");

                loader.restartLoader(LOADER_QUIZ, null, mLoader);
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
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_QUESTIONS:
                return new QuestionsByQuizIdLoader(mContext, quiz.getId());
            case LOADER_ANSWERS:
                return new AnswerByUserAndQuizLoader(
                        mContext, user.getUsername(), quiz.getId());
            case LOADER_QUIZ:
                return new UserQuizByIdAndUserLoader(mContext, quiz.getId(), user.getUsername());
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        switch (loader.getId()) {
            case LOADER_QUESTIONS:
                if (((List<Question>) data).size() > 0) {
                    questions = (List<Question>) data;
                    loaderManager.restartLoader(LOADER_ANSWERS, null, this);
                }
                break;
            case LOADER_ANSWERS:
                for (Answer answer : (List<Answer>) data) {
                    for (Question question : questions) {
                        if (question.getId() == answer.getQuestionID()) {
                            question.setAnswer(answer.getAnswer());
                        }
                    }
                }
                if (userQuiz.getOpenTime() == null) {
                    userQuiz.setOpenTime(Calendar.getInstance().getTime());
                    userQuizRepository.updateUserQuiz(userQuiz);
                }
                Intent intent = new Intent(mContext, QuizActivity.class);
                intent.putExtra(ARG_QUESTIONS, (Serializable) questions);
                intent.putExtra(ARG_QUIZ, quiz);
                intent.putExtra(ARG_USER_QUIZ, userQuiz);
                mContext.startActivity(intent);
                break;
            case LOADER_QUIZ:
                userQuiz = (UserQuiz) data;
                loaderManager.restartLoader(LOADER_QUESTIONS, null, this);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
