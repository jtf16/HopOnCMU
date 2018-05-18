package pt.ulisboa.tecnico.cmov.hoponcmu.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.QuizActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.TermiteManagerActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Answer;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.AnswerOption;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.AnswerRepository;

public class QuizQuestionFragment extends Fragment
        implements View.OnClickListener {

    public static final String ARG_QUESTION = "question";
    public static final String ARG_USERNAME = "username";
    public static final String ARG_QUESTION_NUMBER = "questionNumber";
    private String username;
    private Question question;
    private int questionNumber;
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

    public static QuizQuestionFragment newInstance(Question question, String username, int questionNumber) {

        QuizQuestionFragment fragment = new QuizQuestionFragment();
        Bundle args = new Bundle();

        args.putSerializable(ARG_QUESTION, question);
        args.putString(ARG_USERNAME, username);
        args.putInt(ARG_QUESTION_NUMBER, questionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        answerRepository = new AnswerRepository(getActivity());

        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable(ARG_QUESTION);
            username = getArguments().getString(ARG_USERNAME);
            questionNumber = getArguments().getInt(ARG_QUESTION_NUMBER);
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

        setBtnOption(btnOptionA, question.getOptionA());
        setBtnOption(btnOptionB, question.getOptionB());
        setBtnOption(btnOptionC, question.getOptionC());
        setBtnOption(btnOptionD, question.getOptionD());

        Button submitButton = this.getActivity().findViewById(R.id.submit_btn);

        if (submitButton.getVisibility() == View.GONE) {
            btnOptionA.setEnabled(false);
            btnOptionB.setEnabled(false);
            btnOptionC.setEnabled(false);
            btnOptionD.setEnabled(false);
        }
    }

    private void setBtnOption(Button button, String btnAnswer) {
        button.setText(btnAnswer);
        button.setOnClickListener(this);
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

        TextView currentPageNumber = getActivity().findViewById(R.id.pagination4);
        currentPageNumber.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        currentAnswer = button;
        button.setSelected(true);
    }

    @Override
    public void onClick(View view) {

        String shared = "User: " + username + " Q: " + questionNumber + " A: ";

        if (currentAnswer != null) {
            currentAnswer.setSelected(false);
        }

        setSelectedAnswer((Button) view);
        Answer answer = new Answer(username, question.getQuizID(), question.getId());

        switch (view.getId()) {
            case R.id.option_A:
                answer.setAnswer(AnswerOption.OPTION_A);
                question.setAnswer(AnswerOption.OPTION_A);
                shared += "A";
                break;
            case R.id.option_B:
                answer.setAnswer(AnswerOption.OPTION_B);
                question.setAnswer(AnswerOption.OPTION_B);
                shared += "B";
                break;
            case R.id.option_C:
                answer.setAnswer(AnswerOption.OPTION_C);
                question.setAnswer(AnswerOption.OPTION_C);
                shared += "C";
                break;
            case R.id.option_D:
                answer.setAnswer(AnswerOption.OPTION_D);
                question.setAnswer(AnswerOption.OPTION_D);
                shared += "D";
                break;
        }
        ((QuizActivity) getActivity()).goRight(null);
        answerRepository.insertAnswer(answer);

        new TermiteManagerActivity.SendCommTask().executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR,
                shared);
    }
}
