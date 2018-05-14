package pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import javax.crypto.SecretKey;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.LoginActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.ManagerActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed.DownloadQuizSealedCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.security.SecurityManager;

public class QuizViewHolder extends RecyclerView.ViewHolder {

    private Quiz quiz;
    private User user;
    private long sessionID;
    private SecretKey sharedSecret;

    private ManagerActivity activity;

    private TextView quizNameView;
    private ImageView downloadQuizButtonView;

    private SharedPreferences pref;

    public QuizViewHolder(final Context context, View itemView) {
        super(itemView);

        getSharedPreferences(context);

        this.activity = (ManagerActivity) context;

        quizNameView = itemView.findViewById(R.id.top_string);
        downloadQuizButtonView = itemView.findViewById(R.id.right_image);
        itemView.findViewById(R.id.bottom_string).setVisibility(View.GONE);

        downloadQuizButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadQuizButtonView.setVisibility(View.GONE);
                DownloadQuizSealedCommand dqsc = new DownloadQuizSealedCommand(user.getUsername(),
                        sharedSecret, sessionID, quiz);
                new CommunicationTask(activity, dqsc).execute();
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : Enter inside quiz
                Log.d(MonumentViewHolder.class.getName(),
                        "Element " + getAdapterPosition() + " clicked.");
            }
        });
    }

    private void getSharedPreferences(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = pref.getString(LoginActivity.USER, "");
        user = gson.fromJson(json, User.class);
        sessionID = pref.getLong(LoginActivity.SESSION_ID, -1);
        sharedSecret = SecurityManager.getSecretKey(pref);
    }

    public void setQuiz(Quiz quiz, boolean isDownloaded) {
        this.quiz = quiz;
        quizNameView.setText(quiz.getName());
        if (isDownloaded) {
            downloadQuizButtonView.setVisibility(View.GONE);
        }
    }
}
