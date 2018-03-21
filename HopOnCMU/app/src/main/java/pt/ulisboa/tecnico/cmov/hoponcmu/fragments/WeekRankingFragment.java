package pt.ulisboa.tecnico.cmov.hoponcmu.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;

public class WeekRankingFragment extends Fragment {

    public WeekRankingFragment() {
        // Required empty public constructor
    }

    public static WeekRankingFragment newInstance() {
        WeekRankingFragment fragment = new WeekRankingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_ranking, container, false);
    }
}
