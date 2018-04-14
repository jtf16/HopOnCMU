package pt.ulisboa.tecnico.cmov.hoponcmu.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.QuizActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Answer;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.AnswerOption;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.AnswerRepository;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.QuestionRepository;

public class QuizQuestionFragment extends Fragment
        implements View.OnTouchListener {

    public static final String ARG_PAGE = "page";
    public static final String ARG_QUESTION = "question";
    private int mPageNumber;
    private Question question;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
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

        answerRepository = new AnswerRepository(getActivity());
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
        btnOptionB = view.findViewById(R.id.option_B);
        btnOptionC = view.findViewById(R.id.option_C);
        btnOptionD = view.findViewById(R.id.option_D);

        btnOptionA.setText(question.getOptionA());
        btnOptionA.setOnTouchListener(this);
        btnOptionB.setText(question.getOptionB());
        btnOptionB.setOnTouchListener(this);
        btnOptionC.setText(question.getOptionC());
        btnOptionC.setOnTouchListener(this);
        btnOptionD.setText(question.getOptionD());
        btnOptionD.setOnTouchListener(this);
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

        TextView currentPageNumber = (TextView) getActivity().findViewById(R.id.pagination4);
        currentPageNumber.setTextColor(getResources().getColor(R.color.colorPrimary));

        currentAnswer = button;
        button.setBackgroundResource(R.drawable.button_selected);
        button.setTextColor(getResources().getColor(R.color.colorBright));
    }

    public int getmPageNumber() {
        return mPageNumber;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        if (currentAnswer != null) {
            currentAnswer.setBackgroundResource(R.drawable.button_unselected);
            currentAnswer.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        setSelectedAnswer((Button) view);
        Answer answer = new Answer(((QuizActivity) getActivity()).user.getUsername(),
                question.getQuizID(), question.getId());

        switch (view.getId()) {
            case R.id.option_A:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentAnswer.setBackgroundResource(R.drawable.button_selected_pressed);
                        break;
                    case MotionEvent.ACTION_UP:
                        answer.setAnswer(AnswerOption.OPTION_A);
                        question.setAnswer(AnswerOption.OPTION_A);
                        currentAnswer.setBackgroundResource(R.drawable.button_selected);
                        break;
                }
                break;
            case R.id.option_B:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentAnswer.setBackgroundResource(R.drawable.button_selected_pressed);
                        break;
                    case MotionEvent.ACTION_UP:
                        answer.setAnswer(AnswerOption.OPTION_B);
                        question.setAnswer(AnswerOption.OPTION_B);
                        currentAnswer.setBackgroundResource(R.drawable.button_selected);
                        break;
                }
                break;
            case R.id.option_C:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentAnswer.setBackgroundResource(R.drawable.button_selected_pressed);
                        break;
                    case MotionEvent.ACTION_UP:
                        answer.setAnswer(AnswerOption.OPTION_C);
                        question.setAnswer(AnswerOption.OPTION_C);
                        currentAnswer.setBackgroundResource(R.drawable.button_selected);
                        break;
                }
                break;
            case R.id.option_D:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentAnswer.setBackgroundResource(R.drawable.button_selected_pressed);
                        break;
                    case MotionEvent.ACTION_UP:
                        answer.setAnswer(AnswerOption.OPTION_D);
                        question.setAnswer(AnswerOption.OPTION_D);
                        currentAnswer.setBackgroundResource(R.drawable.button_selected);
                        break;
                }
                break;
        }

        answerRepository.insertAnswer(answer);
        //questionRepository.updateQuestion(question);
        return true;
    }
}
