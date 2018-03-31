package pt.ulisboa.tecnico.cmov.hoponcmu.fragments;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.QuizByPartMonumentNameLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.adapters.DownloadAdapter;

public class DownloadsFragment extends ManagerFragment
        implements LoaderManager.LoaderCallbacks<List<Quiz>> {

    private static final int LOADER_QUIZZES = 1;

    private RecyclerView mRecyclerView;
    private DownloadAdapter downloadAdapter;
    private LinearLayoutManager mLayoutManager;

    private String search = "";

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.downloads_list);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        downloadAdapter = new DownloadAdapter(getActivity(), mLayoutManager, getLoaderManager());
        mRecyclerView.setAdapter(downloadAdapter);
    }

    @Override
    public void refreshSearch(String string) {
        search = string;
        getLoaderManager().restartLoader(LOADER_QUIZZES, null, this);
    }

    @Override
    public Loader<List<Quiz>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_QUIZZES:
                return new QuizByPartMonumentNameLoader(
                        getActivity(), search);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader<List<Quiz>> loader, List<Quiz> data) {
        switch (loader.getId()) {
            case LOADER_QUIZZES:
                downloadAdapter.setQuizzes(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Quiz>> loader) {
        switch (loader.getId()) {
            case LOADER_QUIZZES:
                downloadAdapter.setQuizzes(new ArrayList<Quiz>());
                break;
        }
    }
}
