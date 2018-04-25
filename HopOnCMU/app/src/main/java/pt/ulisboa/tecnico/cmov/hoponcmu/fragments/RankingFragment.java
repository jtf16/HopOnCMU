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
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.ManagerActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.RankingCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.UsersByPartUsernameLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.adapters.UserAdapter;

public class RankingFragment extends ManagerFragment
        implements LoaderManager.LoaderCallbacks {

    private static final int LOADER_USERS = 1;

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private LinearLayoutManager layoutManager;

    private String search = "";

    public RankingFragment() {
        // Required empty public constructor
    }

    public static RankingFragment newInstance() {
        RankingFragment fragment = new RankingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        RankingCommand rc = new RankingCommand();
        new CommunicationTask((ManagerActivity) this.getActivity(), rc).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.fragment_ranking, container, false);

        setRecyclerView(rootView);

        getLoaderManager().restartLoader(LOADER_USERS, null, this);

        return rootView;
    }

    private void setRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.users_list);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        userAdapter = new UserAdapter(getActivity(), layoutManager);
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public void refreshSearch(String string) {
        search = string;
        getLoaderManager().restartLoader(LOADER_USERS, null, this);
    }

    @Override
    public void refreshMonuments() {

    }

    @Override
    public void refreshRanking() {
        getLoaderManager().restartLoader(LOADER_USERS, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_USERS:
                return new UsersByPartUsernameLoader(
                        getActivity(), search);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        switch (loader.getId()) {
            case LOADER_USERS:
                userAdapter.setUsers((List<User>) data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        switch (loader.getId()) {
            case LOADER_USERS:
                userAdapter.setUsers(new ArrayList<User>());
                break;
        }
    }
}
