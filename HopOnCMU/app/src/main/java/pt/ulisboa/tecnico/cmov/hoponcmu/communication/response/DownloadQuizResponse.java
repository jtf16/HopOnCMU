package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

public class DownloadQuizResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String username;
    private Quiz quiz;
    private Question[] questions;

    public DownloadQuizResponse(String username, Quiz quiz, Question[] questions) {
        this.username = username;
        this.quiz = quiz;
        this.questions = questions;
    }

    public String getUsername() {
        return username;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public Question[] getQuestions() {
        return questions;
    }
}
