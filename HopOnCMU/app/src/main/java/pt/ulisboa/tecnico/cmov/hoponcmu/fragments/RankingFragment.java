package pt.ulisboa.tecnico.cmov.hoponcmu.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.adapters.UserAdapter;

public class RankingFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private UserAdapter userAdapter;
    private LinearLayoutManager mLayoutManager;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.fragment_ranking, container, false);

        setRecyclerView(rootView);

        setBasicSample();

        return rootView;
    }

    private void setRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.users_list);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        userAdapter = new UserAdapter(getActivity(), mLayoutManager);
        mRecyclerView.setAdapter(userAdapter);
    }

    private void setBasicSample() {
        User user1 = new User();
        user1.setRanking(1);
        user1.setUserName("sample1");
        user1.setScore(300);
        User user2 = new User();
        user2.setRanking(2);
        user2.setUserName("sample2");
        user2.setScore(200);
        User user3 = new User();
        user3.setRanking(3);
        user3.setUserName("sample3");
        user3.setScore(100);
        User user4 = new User();
        user4.setRanking(4);
        user4.setUserName("sample4");
        user4.setScore(50);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        userAdapter.setUsers(users);
    }
}
