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
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.MainActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.MonumentByPartNameLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.adapters.MonumentAdapter;

public class MonumentsFragment extends ManagerFragment
        implements LoaderManager.LoaderCallbacks<List<Monument>> {

    private static final int LOADER_MONUMENTS_SEARCH = 1;
    private static final int LOADER_MONUMENTS_LOCATION = 2;

    private RecyclerView mRecyclerView;
    private MonumentAdapter monumentAdapter;
    private LinearLayoutManager mLayoutManager;

    private String search = "";

    public MonumentsFragment() {
        // Required empty public constructor
    }

    public static MonumentsFragment newInstance() {
        MonumentsFragment fragment = new MonumentsFragment();
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
                R.layout.fragment_monuments, container, false);

        getLoaderManager().restartLoader(LOADER_MONUMENTS_SEARCH, null, this);

        setRecyclerView(rootView);

        return rootView;
    }

    private void setRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.monuments_list);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        monumentAdapter = new MonumentAdapter(getActivity(), mLayoutManager, getLoaderManager());
        mRecyclerView.setAdapter(monumentAdapter);
    }

    @Override
    public Loader<List<Monument>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_MONUMENTS_SEARCH:
            case LOADER_MONUMENTS_LOCATION:
                return new MonumentByPartNameLoader(
                        getActivity(), search, MainActivity.getmLastLocation());
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader<List<Monument>> loader, List<Monument> data) {
        switch (loader.getId()) {
            case LOADER_MONUMENTS_SEARCH:
                monumentAdapter.scrollToTop();
            case LOADER_MONUMENTS_LOCATION:
                monumentAdapter.setMonuments(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Monument>> loader) {
        switch (loader.getId()) {
            case LOADER_MONUMENTS_SEARCH:
            case LOADER_MONUMENTS_LOCATION:
                monumentAdapter.setMonuments(new ArrayList<Monument>());
                break;
        }
    }

    @Override
    public void refreshSearch(String string) {
        search = string;
        getLoaderManager().restartLoader(LOADER_MONUMENTS_SEARCH, null, this);
    }

    @Override
    public void refreshMonuments() {
        getLoaderManager().restartLoader(LOADER_MONUMENTS_LOCATION, null, this);
    }

    @Override
    public void refreshRanking() {

    }
}
