package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;

public class DownloadQuizCommand implements Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private String username;
    private long sessionID;
    private Monument monument;

    public DownloadQuizCommand(String username, long sessionID, Monument monument) {
        this.username = username;
        this.sessionID = sessionID;
        this.monument = monument;
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

    public Monument getMonument() {
        return monument;
    }
}
