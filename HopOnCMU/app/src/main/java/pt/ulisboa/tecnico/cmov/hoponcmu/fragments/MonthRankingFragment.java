package pt.ulisboa.tecnico.cmov.hoponcmu.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;

public class MonthRankingFragment extends Fragment {

    public MonthRankingFragment() {
        // Required empty public constructor
    }

    public static MonthRankingFragment newInstance() {
        MonthRankingFragment fragment = new MonthRankingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_month_ranking, container, false);
    }
}
