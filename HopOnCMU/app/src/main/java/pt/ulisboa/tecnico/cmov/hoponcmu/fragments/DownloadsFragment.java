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
import pt.ulisboa.tecnico.cmov.hoponcmu.data.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.adapters.DownloadAdapter;

public class DownloadsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DownloadAdapter downloadAdapter;
    private LinearLayoutManager mLayoutManager;

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

        setBasicSample();

        return rootView;
    }

    private void setRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.downloads_list);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        downloadAdapter = new DownloadAdapter(getActivity(), mLayoutManager);
        mRecyclerView.setAdapter(downloadAdapter);
    }

    private void setBasicSample() {
        Quiz quiz1 = new Quiz();
        quiz1.setName("Ponte 25 de Abril");
        quiz1.setNumberOfQuestions(20);
        Quiz quiz2 = new Quiz();
        quiz2.setName("Torre de Belem");
        quiz2.setNumberOfQuestions(30);
        Quiz quiz3 = new Quiz();
        quiz3.setName("Palácio da Pena");
        quiz3.setNumberOfQuestions(7);
        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(quiz1);
        quizzes.add(quiz2);
        quizzes.add(quiz3);
        downloadAdapter.setQuizzes(quizzes);
    }
}
