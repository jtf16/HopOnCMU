package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;

public class DownloadQuizCommand implements Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private Monument monument;

    public DownloadQuizCommand(Monument monument) {
        this.monument = monument;
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }

    public Monument getMonument() {
        return monument;
    }
}
