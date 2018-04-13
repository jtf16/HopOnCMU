package pt.ulisboa.tecnico.cmov.hoponcmu.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.AnswerOption;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.QuestionRepository;

public class QuizQuestionFragment extends Fragment
        implements View.OnClickListener {

    public static final String ARG_PAGE = "page";
    public static final String ARG_QUESTION = "question";
    Drawable btn_default_drawable;
    int btn_default_text_color;
    private int mPageNumber;
    private Question question;
    private QuestionRepository questionRepository;
    private TextView textQuestion;
    private Button btnOptionA;
    private Button btnOptionB;
    private Button btnOptionC;
    private Button btnOptionD;
    private Button currentAnswer = null;

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

        questionRepository = new QuestionRepository(getActivity());

        if (getArguments() != null) {
            mPageNumber = getArguments().getInt(ARG_PAGE);
            question = (Question) getArguments().getSerializable(ARG_QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.fragment_quiz_question, container, false);

        setQuestionAndOptions(rootView);

        setAnswer();

        return rootView;
    }

    private void setQuestionAndOptions(View view) {
        textQuestion = view.findViewById(R.id.question);
        textQuestion.setText(question.getQuestion());
        btnOptionA = view.findViewById(R.id.option_A);
        btn_default_text_color = btnOptionA.getCurrentTextColor();
        btn_default_drawable = btnOptionA.getBackground();
        btnOptionA.setText(question.getOptionA());
        btnOptionA.setOnClickListener(this);
        btnOptionB = view.findViewById(R.id.option_B);
        btnOptionB.setText(question.getOptionB());
        btnOptionB.setOnClickListener(this);
        btnOptionC = view.findViewById(R.id.option_C);
        btnOptionC.setText(question.getOptionC());
        btnOptionC.setOnClickListener(this);
        btnOptionD = view.findViewById(R.id.option_D);
        btnOptionD.setText(question.getOptionD());
        btnOptionD.setOnClickListener(this);
    }

    private void setAnswer() {
        AnswerOption answer = question.getAnswer();

        if (answer != null) {
            switch (answer) {
                case OPTION_A:
                    setSelectedAnswer(btnOptionA);
                    break;
                case OPTION_B:
                    setSelectedAnswer(btnOptionB);
                    break;
                case OPTION_C:
                    setSelectedAnswer(btnOptionC);
                    break;
                case OPTION_D:
                    setSelectedAnswer(btnOptionD);
                    break;
            }
        }
    }

    private void setSelectedAnswer(Button button) {
        currentAnswer = button;
        button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        button.setTextColor(getResources().getColor(R.color.colorBright));
    }

    public int getmPageNumber() {
        return mPageNumber;
    }

    @Override
    public void onClick(View view) {
        if (currentAnswer != null) {
            currentAnswer.setBackgroundDrawable(btn_default_drawable);
            currentAnswer.setTextColor(btn_default_text_color);
        }
        setSelectedAnswer((Button) view);
        switch (view.getId()) {
            case R.id.option_A:
                question.setAnswer(AnswerOption.OPTION_A);
                break;
            case R.id.option_B:
                question.setAnswer(AnswerOption.OPTION_B);
                break;
            case R.id.option_C:
                question.setAnswer(AnswerOption.OPTION_C);
                break;
            case R.id.option_D:
                question.setAnswer(AnswerOption.OPTION_D);
                break;
        }
        questionRepository.updateQuestion(question);
    }
}
