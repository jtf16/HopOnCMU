package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class SetUpResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private Quiz[] quizzes;
    private Monument[] monuments;
    private User[] users;

    public SetUpResponse(Quiz[] quizzes, Monument[] monuments, User[] users) {
        this.monuments = monuments;
        this.quizzes = quizzes;
        this.users = users;
    }

    public Quiz[] getQuizzes() {
        return quizzes;
    }

    public Monument[] getMonuments() {
        return monuments;
    }

    public User[] getUsers() {
        return users;
    }
}
