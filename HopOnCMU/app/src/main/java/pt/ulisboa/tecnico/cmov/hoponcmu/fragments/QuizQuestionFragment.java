package pt.ulisboa.tecnico.cmov.hoponcmu.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders.QuestionsByIDLoader;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

public class QuizQuestionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Question>{

    private static final int LOADER_QUESTION = 1;

    public static final String ARG_PAGE = "page";
    public static final String ARG_QUESTION = "question";

    private int mPageNumber;
    private Question question;

    private TextView textQuestion;
    private Button btnOptionA;
    private Button btnOptionB;
    private Button btnOptionC;
    private Button btnOptionD;

    public QuizQuestionFragment() {
        // Required empty public constructor
    }

    public static QuizQuestionFragment newInstance(int pageNumber, Question question) {
        QuizQuestionFragment fragment = new QuizQuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putSerializable(ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPageNumber = getArguments().getInt(ARG_PAGE);
            question = (Question) getArguments().getSerializable(ARG_QUESTION);
        }

        getLoaderManager().restartLoader(LOADER_QUESTION, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.fragment_quiz_question, container, false);

        setQuestionAndOptions(rootView);

        return rootView;
    }

    private void setQuestionAndOptions(View view) {
        textQuestion = view.findViewById(R.id.question);
        textQuestion.setText(question.getQuestion());
        btnOptionA = view.findViewById(R.id.option_A);
        btnOptionA.setText(question.getOptionA());
        btnOptionB = view.findViewById(R.id.option_B);
        btnOptionB.setText(question.getOptionB());
        btnOptionC = view.findViewById(R.id.option_C);
        btnOptionC.setText(question.getOptionC());
        btnOptionD = view.findViewById(R.id.option_D);
        btnOptionD.setText(question.getOptionD());
    }

    public int getmPageNumber() {
        return mPageNumber;
    }

    public void setAnswer(View view) {

        Button button = (Button) view;

        button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        button.setTextColor(getResources().getColor(R.color.colorBright));
    }

    @Override
    public Loader<Question> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_QUESTION:
                return new QuestionsByIDLoader(
                        getActivity(), question.getId());
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(Loader<Question> loader, Question data) {
        switch (loader.getId()) {
            case LOADER_QUESTION:
                // TODO: change button color
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Question> loader) {

    }
}
