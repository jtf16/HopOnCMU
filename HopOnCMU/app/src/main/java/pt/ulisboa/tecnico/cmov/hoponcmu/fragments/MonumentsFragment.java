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
import pt.ulisboa.tecnico.cmov.hoponcmu.data.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.adapters.MonumentAdapter;

public class MonumentsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MonumentAdapter monumentAdapter;
    private LinearLayoutManager mLayoutManager;

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

        setRecyclerView(rootView);

        setBasicSample();

        return rootView;
    }

    private void setRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.monuments_list);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        monumentAdapter = new MonumentAdapter(getActivity(), mLayoutManager);
        mRecyclerView.setAdapter(monumentAdapter);
    }

    private void setBasicSample() {
        Monument monument1 = new Monument();
        monument1.setName("Ponte 25 de Abril");
        monument1.setDistance(4000);
        Monument monument2 = new Monument();
        monument2.setName("Torre de Belem");
        monument2.setDistance(530);
        Monument monument3 = new Monument();
        monument3.setName("Palácio da Pena");
        monument3.setDistance(10750);
        Monument monument4 = new Monument();
        monument4.setName("Ponte 25 de Abril");
        monument4.setDistance(100);
        List<Monument> monuments = new ArrayList<>();
        monuments.add(monument1);
        monuments.add(monument2);
        monuments.add(monument3);
        monuments.add(monument4);
        monumentAdapter.setUsers(monuments);
    }
}
