package pt.ulisboa.tecnico.cmov.hoponcmu.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;

public class AllTimeRankingFragment extends Fragment {

    public AllTimeRankingFragment() {
        // Required empty public constructor
    }

    public static AllTimeRankingFragment newInstance() {
        AllTimeRankingFragment fragment = new AllTimeRankingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_time_ranking, container, false);
    }
}
