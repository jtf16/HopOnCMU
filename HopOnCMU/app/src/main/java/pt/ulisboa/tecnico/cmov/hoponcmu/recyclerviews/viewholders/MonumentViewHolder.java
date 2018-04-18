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
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.LoginActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.ManagerActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.MonumentActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.DownloadQuizCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.QuizByMonumentIdLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class MonumentViewHolder extends RecyclerView.ViewHolder
        implements LoaderManager.LoaderCallbacks {

    private static final String ARG_QUIZZES = "quizzes";
    private static final String ARG_MONUMENT = "monument";
    private static final int LOADER_QUIZ = 1;
    LoaderManager loaderManager;
    private TextView name;
    private TextView distance;
    private Monument monument;
    private ManagerActivity activity;
    private SharedPreferences pref;
    private User user;
    private List<Quiz> quizList;
    private MonumentViewHolder mLoader = this;

    private Context mContext;

    public MonumentViewHolder(Context context, View itemView, final LoaderManager loader) {
        super(itemView);

        this.mContext = context;
        loaderManager = loader;

        pref = PreferenceManager.getDefaultSharedPreferences(context);
        // Define click listener for the ViewHolder's View.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String json = pref.getString(LoginActivity.USER, "");
                user = gson.fromJson(json, User.class);
                long sessionId = pref.getLong(LoginActivity.SESSION_ID, -1);
                DownloadQuizCommand dqc = new DownloadQuizCommand(
                        user.getUsername(), sessionId, monument);
                new CommunicationTask(activity, dqc).execute();
                Log.d(MonumentViewHolder.class.getName(),
                        "Element " + getAdapterPosition() + " clicked.");


                loader.restartLoader(LOADER_QUIZ, null, mLoader);

            }
        });
        name = itemView.findViewById(R.id.monument_name);
        distance = itemView.findViewById(R.id.monument_distance);
    }

    public void setMonument(ManagerActivity managerActivity, Monument monument) {
        this.activity = managerActivity;
        this.monument = monument;
        name.setText(monument.getName());
        double floatDistance = (double) monument.getCosDistance();
        if (floatDistance == 0) {
            distance.setVisibility(View.INVISIBLE);
        } else {
            distance.setVisibility(View.VISIBLE);
            floatDistance = 6371000 * Math.acos(floatDistance);
            if (floatDistance >= 1000) {
                distance.setText(String.format("%.1fKm", floatDistance / 1000));
            } else {
                distance.setText(String.format("%dm", (int) floatDistance));
            }
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        switch (id) {
            case LOADER_QUIZ:
                return new QuizByMonumentIdLoader(mContext, monument.getId());
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        switch (loader.getId()) {
            case LOADER_QUIZ:
                quizList = (List<Quiz>) data;
                Intent intent = new Intent(mContext, MonumentActivity.class);
                intent.putExtra(ARG_QUIZZES, (Serializable) quizList);
                intent.putExtra(ARG_MONUMENT, monument);
                mContext.startActivity(intent);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
