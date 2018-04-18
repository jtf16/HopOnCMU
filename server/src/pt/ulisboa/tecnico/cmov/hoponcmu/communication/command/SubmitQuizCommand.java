package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.UserQuiz;

public class SubmitQuizCommand implements Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private long sessionID;
    private UserQuiz userQuiz;
    private List<Question> questions;

    public SubmitQuizCommand(long sessionID, UserQuiz userQuiz, List<Question> questions) {
        this.sessionID = sessionID;
        this.userQuiz = userQuiz;
        this.questions = questions;
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }

    public long getSessionID() {
        return sessionID;
    }

    public UserQuiz getUserQuiz() {
        return userQuiz;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
