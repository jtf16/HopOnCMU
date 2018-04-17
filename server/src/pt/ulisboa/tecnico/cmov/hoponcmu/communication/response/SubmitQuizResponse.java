package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

public class SubmitQuizResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private Quiz quiz;

    public SubmitQuizResponse(Quiz quiz) {
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }
}
