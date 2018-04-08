package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

public class DownloadQuizResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private Quiz quiz;
    private Question[] questions;

    public DownloadQuizResponse(Quiz quiz, Question[] questions) {
        this.quiz = quiz;
        this.questions = questions;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public Question[] getQuestions() {
        return questions;
    }
}
