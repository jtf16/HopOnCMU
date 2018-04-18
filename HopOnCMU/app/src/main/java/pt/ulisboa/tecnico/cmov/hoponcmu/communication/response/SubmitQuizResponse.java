package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.UserQuiz;

public class SubmitQuizResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private UserQuiz userQuiz;

    public SubmitQuizResponse(UserQuiz userQuiz) {
        this.userQuiz = userQuiz;
    }

    public UserQuiz getUserQuiz() {
        return userQuiz;
    }
}
