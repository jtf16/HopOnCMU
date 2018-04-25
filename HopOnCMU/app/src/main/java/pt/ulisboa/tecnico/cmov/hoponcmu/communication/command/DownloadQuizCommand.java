package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

public class DownloadQuizCommand implements Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private String username;
    private long sessionID;
    private Quiz quiz;

    public DownloadQuizCommand(String username, long sessionID, Quiz quiz) {
        this.username = username;
        this.sessionID = sessionID;
        this.quiz = quiz;
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

    public Quiz getQuiz() {
        return quiz;
    }
}
