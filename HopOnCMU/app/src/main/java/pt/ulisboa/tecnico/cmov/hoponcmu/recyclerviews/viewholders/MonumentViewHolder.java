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
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.MonumentActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.QuizByMonumentIdLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.QuizIDsByMonumentAndUserLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class MonumentViewHolder extends RecyclerView.ViewHolder
        implements LoaderManager.LoaderCallbacks {

    private static final String ARG_QUIZZES = "quizzes";
    private static final String ARG_IDS = "ids";
    private static final String ARG_MONUMENT = "monument";

    private static final int LOADER_QUIZ = 1;
    private static final int LOADER_QUIZ_DOWNLOADED = 2;

    private User user;
    private Monument monument;

    private Context context;

    private LoaderManager loaderManager;

    private TextView monumentNameView;
    private TextView monumentDistanceView;

    private SharedPreferences pref;

    private MonumentViewHolder mLoader = this;

    private List<Quiz> quizList;
    private List<Long> listIDs;

    public MonumentViewHolder(Context context, View itemView, final LoaderManager loader) {
        super(itemView);

        this.context = context;
        loaderManager = loader;

        monumentNameView = itemView.findViewById(R.id.top_string);
        monumentDistanceView = itemView.findViewById(R.id.bottom_string);
        itemView.findViewById(R.id.right_image).setVisibility(View.GONE);

        getSharedPreferences(context);

        // Define click listener for the ViewHolder's View.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(MonumentViewHolder.class.getName(),
                        "Element " + getAdapterPosition() + " clicked.");

                loader.restartLoader(LOADER_QUIZ, null, mLoader);

            }
        });
    }

    private void getSharedPreferences(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = pref.getString(LoginActivity.USER, "");
        user = gson.fromJson(json, User.class);
    }

    public void setMonument(Monument monument) {
        this.monument = monument;
        monumentNameView.setText(context.getString(
                R.string.monument_name, monument.getName()));
        double floatDistance = monument.getCosDistance();
        if (floatDistance == 0) {
            monumentDistanceView.setVisibility(View.INVISIBLE);
        } else {
            monumentDistanceView.setVisibility(View.VISIBLE);
            floatDistance = 6371000 * Math.acos(floatDistance);
            if (floatDistance >= 1000) {
                monumentDistanceView.setText(context.getString(
                        R.string.monument_distance_Km, floatDistance / 1000));
            } else {
                monumentDistanceView.setText(context.getString(
                        R.string.monument_distance_meters, (int) floatDistance));
            }
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        switch (id) {
            case LOADER_QUIZ:
                return new QuizByMonumentIdLoader(context, monument.getId());
            case LOADER_QUIZ_DOWNLOADED:
                return new QuizIDsByMonumentAndUserLoader(context, monument.getId(), user.getUsername());
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        switch (loader.getId()) {
            case LOADER_QUIZ:
                quizList = (List<Quiz>) data;
                loaderManager.restartLoader(LOADER_QUIZ_DOWNLOADED, null, mLoader);
                break;
            case LOADER_QUIZ_DOWNLOADED:
                listIDs = (List<Long>) data;
                Intent intent = new Intent(context, MonumentActivity.class);
                intent.putExtra(ARG_QUIZZES, (Serializable) quizList);
                intent.putExtra(ARG_IDS, (Serializable) listIDs);
                intent.putExtra(ARG_MONUMENT, monument);
                context.startActivity(intent);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
