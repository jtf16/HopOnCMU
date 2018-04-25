package pt.ulisboa.tecnico.cmov.hoponcmu.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.LoginActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.QuizByPartMonumentNameAndUsernameLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.adapters.DownloadAdapter;

public class DownloadsFragment extends ManagerFragment
        implements LoaderManager.LoaderCallbacks {

    private static final int LOADER_QUIZZES = 1;

    private RecyclerView recyclerView;
    private DownloadAdapter downloadAdapter;
    private LinearLayoutManager layoutManager;

    private String search = "";
    private User user;

    public DownloadsFragment() {
        // Required empty public constructor
    }

    public static DownloadsFragment newInstance() {
        DownloadsFragment fragment = new DownloadsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = pref.edit();
        Gson gson = new Gson();
        String json = pref.getString(LoginActivity.USER, "");
        user = gson.fromJson(json, User.class);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.fragment_downloads, container, false);

        setRecyclerView(rootView);

        getLoaderManager().restartLoader(LOADER_QUIZZES, null, this);

        return rootView;
    }

    private void setRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.downloads_list);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        downloadAdapter = new DownloadAdapter(getActivity(), layoutManager, getLoaderManager());
        recyclerView.setAdapter(downloadAdapter);
    }

    @Override
    public void refreshSearch(String string) {
        search = string;
        getLoaderManager().restartLoader(LOADER_QUIZZES, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_QUIZZES:
                return new QuizByPartMonumentNameAndUsernameLoader(
                        getActivity(), search, user.getUsername());
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        switch (loader.getId()) {
            case LOADER_QUIZZES:
                downloadAdapter.setQuizzes((List<Quiz>) data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        switch (loader.getId()) {
            case LOADER_QUIZZES:
                downloadAdapter.setQuizzes(new ArrayList<Quiz>());
                break;
        }
    }

    @Override
    public void refreshMonuments() {

    }

    @Override
    public void refreshRanking() {

    }
}
