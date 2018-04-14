package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;

public class SubmitQuizCommand implements Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private String username;
    private long sessionID;
    private List<Question> questions;

    public SubmitQuizCommand(String username, long sessionID, List<Question> questions) {
        this.username = username;
        this.sessionID = sessionID;
        this.questions = questions;
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }

    public String getUsername() {
        return username;
    }

    public long getSessionID() {
        return sessionID;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
